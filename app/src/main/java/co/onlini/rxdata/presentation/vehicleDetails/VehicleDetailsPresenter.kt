/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.presentation.vehicleDetails

import co.onlini.rxdata.data.VehiclesRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber


internal class VehicleDetailsPresenter(private val vehicleId: Long,
                                       private val view: VehicleDetailsContract.View,
                                       private val vehiclesRepo: VehiclesRepo) : VehicleDetailsContract.Presenter {

    private val disposable = CompositeDisposable()

    override fun onViewStart() {
        if (disposable.size() > 0) {
            Timber.w("Unexpected subscriptions")
            disposable.clear()
        }
        disposable.add(subscribeVehicleData())
    }

    private fun subscribeVehicleData(): Disposable? {
        return vehiclesRepo.getVehicle(vehicleId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            optionalVehicle ->
                            val vehicle = optionalVehicle.value
                            if (vehicle != null) {
                                view.applyVehicle(vehicle)
                            } else {
                                view.showUnableToLoadVehicle()
                            }
                        },
                        {
                            view.showUnableToLoadVehicle()
                        }
                )
    }

    override fun onViewStop() {
        disposable.clear()
    }
}