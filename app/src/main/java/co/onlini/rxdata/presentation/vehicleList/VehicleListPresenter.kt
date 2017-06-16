/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.presentation.vehicleList

import co.onlini.rxdata.data.VehicleDataFetcher
import co.onlini.rxdata.data.VehiclesRepo
import co.onlini.rxdata.data.model.FetchingError
import co.onlini.rxdata.data.model.FetchingState
import co.onlini.rxdata.data.model.Vehicle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


internal class VehicleListPresenter(private val view: VehicleListContract.View,
                                    private val vehiclesRepo: VehiclesRepo,
                                    private val vehicleDataFetcher: VehicleDataFetcher) : VehicleListContract.Presenter {
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun vehicleItemClicked(vehicle: Vehicle) {
        view.goToVehicleDetails(vehicle)
    }

    override fun requestDataFetching() {
        vehicleDataFetcher.fetchData()
    }

    override fun onViewCreatedFirstTime() {
        vehicleDataFetcher.fetchData()
    }

    override fun onViewStart() {
        if (disposable.size() > 0) {
            timber.log.Timber.w("Unexpected subscriptions")
            disposable.clear()
        }
        subscribeForSingleTransactionTest()
        disposable.add(subscribeVehicleDataChanges())
        disposable.add(subscribeFetchingStates())
        disposable.add(subscribeFetchingError())
    }

    override fun onViewStop() {
        disposable.clear()
    }

    private fun subscribeFetchingError(): Disposable? {
        return vehicleDataFetcher.getSubsequentFetchingErrors()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            nextFetchingError ->
                            view.showFetchingError(nextFetchingError)
                        }
                )
    }

    private fun subscribeFetchingStates(): Disposable? {
        return vehicleDataFetcher.getMostRecentAndSubsequentFetchingStates()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            state ->
                            if (state == FetchingState.IDLE) {
                                view.hideFetchingListProgressView()
                            } else {
                                view.showFetchingListProgressView()
                            }
                        }
                )
    }

    private fun subscribeVehicleDataChanges(): Disposable? {
        return vehiclesRepo.getVehicles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            vehicles ->
                            view.applyVehicleList(vehicles)
                        },
                        {
                            view.showFetchingError(FetchingError.OTHER)
                        }
                )
    }

    /**
     * Shows how SQLBrite data change notification works with subscribers when data of multiple
     * tables has been changed within a single transaction
     */
    fun subscribeForSingleTransactionTest() {
        disposable.add(vehiclesRepo.getPateNumbersOwnerName()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    timber.log.Timber.i("onNext: vehicle + people")
                }))
        disposable.add(vehiclesRepo.getVehicles()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    timber.log.Timber.i("onNext: vehicle ")
                }))
        disposable.add(vehiclesRepo.getPeople()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    timber.log.Timber.i("onNext: people")
                }))
    }

}
