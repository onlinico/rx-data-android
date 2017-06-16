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
import co.onlini.rxdata.data.storage.Dao
import io.reactivex.Observable

class VehiclesRepoImpl(private val dao: Dao) : VehiclesRepo {

    override fun getVehicle(vehicleId: Long): Observable<Optional<Vehicle>> = dao.getVehicle(vehicleId)

    override fun getVehicles(): Observable<List<Vehicle>> = dao.getVehiclesAsList()

    override fun getPeople(): Observable<List<Person>> = dao.getPeople()

    override fun getVehicleOwnersByName(name: String): Observable<List<Vehicle>> = dao.getVehicleOwnerListByName(name)

    override fun getPateNumbersOwnerName(): Observable<List<PlateNumberOwnerName>> = dao.getPlateNumberOwnerName()
}