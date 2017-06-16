/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data.storage

import android.database.Cursor
import co.onlini.rxdata.data.model.Optional
import co.onlini.rxdata.data.model.Person
import co.onlini.rxdata.data.model.PlateNumberOwnerName
import co.onlini.rxdata.data.model.Vehicle
import com.squareup.sqlbrite2.SqlBrite
import io.reactivex.Observable

interface Dao {
    fun getVehicleNonObservable(vehicleId: Long): Vehicle?
    fun getVehicle(vehicleId: Long): Observable<Optional<Vehicle>>
    fun getVehiclesAsList(): Observable<List<Vehicle>>
    fun getVehiclesAsCursor(): Observable<Cursor>
    fun getVehiclesAsQuery(): Observable<SqlBrite.Query>
    fun getVehicleOwnerListByName(name: String): Observable<List<Vehicle>>
    fun getPeople(): Observable<List<Person>>
    fun getPlateNumberOwnerName(): Observable<List<PlateNumberOwnerName>>
    fun deleteVehicle(vehicle: Vehicle)
    fun deleteVehiclesByOwner(ownerId: Long)
    fun insertOrReplaceVehicle(vehicle: Vehicle)
    fun saveOwnersVehiclesWithinSingleTransaction(ownersVehicles: Map<Person, List<Vehicle>>)
    fun saveFollowingPeopleRemoveRest(people: List<Person>)
}