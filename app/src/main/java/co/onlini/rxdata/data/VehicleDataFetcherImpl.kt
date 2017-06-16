/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data

import co.onlini.rxdata.data.model.*
import co.onlini.rxdata.data.storage.Dao
import co.onlini.rxdata.data.webapi.WebService
import co.onlini.rxdata.data.webapi.dto.VehicleOwnerDto
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class VehicleDataFetcherImpl(private val dao: Dao,
                             private val webService: WebService) : VehicleDataFetcher {
    override fun fetchData() {
        fetchingStatesSubject
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .take(1)
                .filter { it == FetchingState.IDLE }
                .doOnNext {
                    fetchingStatesSubject.onNext(FetchingState.IN_PROGRESS)
                }
                .flatMap {
                    webService.getVehicleOwners()
                }
                .doFinally { fetchingStatesSubject.onNext(FetchingState.IDLE) }
                .subscribe(
                        {
                            response ->
                            processFetchingResponse(response)
                        },
                        {
                            error ->
                            Timber.e("Fetching data error ${error.message}")
                            if (error is IOException) {
                                fetchingErrorsSubject.onNext(FetchingError.CONNECTION_ISSUE)
                            } else {
                                fetchingErrorsSubject.onNext(FetchingError.INTERNAL_ERROR)
                            }
                        }
                )
    }

    private fun processFetchingResponse(response: Response<List<VehicleOwnerDto>>) {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                val ownersVehicles = mapToPersonVehicles(body)
                dao.saveOwnersVehiclesWithinSingleTransaction(ownersVehicles)
            } else {
                fetchingErrorsSubject.onNext(FetchingError.INVALID_RESPONSE)
            }
        } else {
            val fetchingError = when (response.code()) {
                401 -> FetchingError.UNAUTHORIZED
            //  and so on...
                else -> FetchingError.OTHER
            }
            fetchingErrorsSubject.onNext(fetchingError)
        }
    }

    private fun mapToPersonVehicles(list: List<VehicleOwnerDto>): Map<Person, List<Vehicle>> {
        val map: MutableMap<Person, List<Vehicle>> = mutableMapOf()
        list.forEach {
            ownerDto ->
            val vehicles = ownerDto.vehicles.map {
                vehicleDto ->
                Vehicle(vehicleDto.id, Vin(vehicleDto.vin), ownerDto.id, vehicleDto.plateNumber,
                        vehicleDto.manufacturer, vehicleDto.model, vehicleDto.bodyType.toBodyType(), vehicleDto.color)

            }
            val person = Person(ownerDto.id, ownerDto.firstName, ownerDto.lastName, ownerDto.phone)
            map.put(person, vehicles)
        }
        return map
    }

    override fun getMostRecentAndSubsequentFetchingStates(): Observable<FetchingState> {
        return fetchingStatesSubject
                .doOnComplete { Timber.e("Observable<FetchingState> completed, it shouldn't happen") }
                .doOnError { Timber.e("Observable<FetchingState> exception", it) }
    }

    override fun getSubsequentFetchingErrors(): Observable<FetchingError> {
        return fetchingErrorsSubject
                .doOnComplete { Timber.e("Observable<FetchingError> completed, it shouldn't happen") }
                .doOnError { Timber.e("Observable<FetchingError> exception", it) }
    }

    private var fetchingStatesSubject: BehaviorSubject<FetchingState> = BehaviorSubject.createDefault(FetchingState.IDLE)
    private var fetchingErrorsSubject: PublishSubject<FetchingError> = PublishSubject.create()
}