/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data

import co.onlini.rxdata.data.model.Optional
import co.onlini.rxdata.data.model.Person
import co.onlini.rxdata.data.model.PlateNumberOwnerName
import co.onlini.rxdata.data.model.Vehicle
import io.reactivex.Observable

interface VehiclesRepo {
    fun getVehicle(vehicleId: Long): Observable<Optional<Vehicle>>
    fun getVehicles(): Observable<List<Vehicle>>
    fun getVehicleOwnersByName(name: String): Observable<List<Vehicle>>
    fun getPateNumbersOwnerName(): Observable<List<PlateNumberOwnerName>>
    fun getPeople(): Observable<List<Person>>
}