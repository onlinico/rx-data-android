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
import com.squareup.sqlbrite2.BriteDatabase
import com.squareup.sqlbrite2.SqlBrite
import io.reactivex.Observable

class DaoImpl(private val brite: BriteDatabase) : Dao {

    override fun getVehicleNonObservable(vehicleId: Long): Vehicle? {
        val statement = Vehicle.FACTORY.select_vehicle_by_id(vehicleId)
        var vehicle: Vehicle? = null
        brite.readableDatabase.rawQuery(statement.statement, statement.args).use {
            vehicle = Vehicle.MAPPER.map(it)
        }
        return vehicle
    }

    override fun getVehicle(vehicleId: Long): Observable<Optional<Vehicle>> {
        val statement = Vehicle.FACTORY.select_vehicle_by_id(vehicleId)
        return brite.createQuery(statement.tables, statement.statement, *statement.args)
                .mapToList { cursor -> Vehicle.MAPPER.map(cursor) }
                .map { list -> if (list.size > 0) Optional(list[0]) else Optional(null) }
    }

    override fun getVehiclesAsList(): Observable<List<Vehicle>> {
        val statement = Vehicle.FACTORY.select_all_vehicles()
        return brite.createQuery(statement.tables, statement.statement, *statement.args)
                .mapToList { cursor -> Vehicle.MAPPER.map(cursor) }
    }

    override fun getVehiclesAsCursor(): Observable<Cursor> {
        val statement = Vehicle.FACTORY.select_all_vehicles()
        return brite.createQuery(statement.tables, statement.statement, *statement.args)
                .map { query: SqlBrite.Query -> query.run() }
    }

    override fun getVehiclesAsQuery(): Observable<SqlBrite.Query> {
        val statement = Vehicle.FACTORY.select_all_vehicles()
        return brite.createQuery(statement.tables, statement.statement, *statement.args)
    }

    override fun getVehicleOwnerListByName(name: String): Observable<List<Vehicle>> {
        val statement = Vehicle.FACTORY.select_vehicles_by_owner_name(name)
        return brite.createQuery(statement.tables, statement.statement, *statement.args)
                .mapToList { cursor: Cursor -> Vehicle.MAPPER.map(cursor) }
    }

    override fun getPlateNumberOwnerName(): Observable<List<PlateNumberOwnerName>> {
        val statement = Vehicle.FACTORY.select_vehicle_plate_number_and_owner_name()
        return brite.createQuery(statement.tables, statement.statement, *statement.args)
                .mapToList { cursor -> PlateNumberOwnerName.MAPPER.map(cursor) }
    }

    override fun getPeople(): Observable<List<Person>> {
        val statement = Person.FACTORY.select_people()
        return brite.createQuery(statement.tables, statement.statement, *statement.args)
                .mapToList { cursor -> Person.MAPPER.map(cursor) }
    }

    override fun deleteVehiclesByOwner(ownerId: Long) {
        val statement = VehicleModel.Delete_vehicles_by_owner(brite.writableDatabase)
        statement.bind(ownerId)
        brite.executeUpdateDelete(statement.table, statement.program)
    }

    override fun deleteVehicle(vehicle: Vehicle) {
        val statement = VehicleModel.Delete_vehicle(brite.writableDatabase)
        statement.bind(vehicle.id)
        brite.executeUpdateDelete(statement.table, statement.program)
    }

    override fun insertOrReplaceVehicle(vehicle: Vehicle) {
        val statement = VehicleModel.Insert_or_replace_vehicle(brite.writableDatabase, Vehicle.FACTORY)
        vehicle.apply {
            statement.bind(id, vin, ownerId, plateNumber, manufacturer, model, bodyType, color)
        }
        brite.executeInsert(statement.table, statement.program)
    }

    override fun saveOwnersVehiclesWithinSingleTransaction(ownersVehicles: Map<Person, List<Vehicle>>) {
        val transaction = brite.newTransaction()
        try {
//            val deleteVehiclesByOwnerStatement = VehicleModel.Delete_vehicles_by_owner(brite.writableDatabase)
            val insertVehicleStatement = VehicleModel.Insert_or_replace_vehicle(brite.writableDatabase, Vehicle.FACTORY)
            val deletePersonAllStatement = PersonModel.Delete_person_all(brite.writableDatabase)
            val insertPersonStatement = PersonModel.Insert_person(brite.writableDatabase)

            brite.executeUpdateDelete(deletePersonAllStatement.table, deletePersonAllStatement.program)
            ownersVehicles.forEach { owner, vehicles ->
                insertPersonStatement.bind(owner.id, owner.firstName, owner.lastName, owner.phone)
                brite.executeInsert(insertPersonStatement.table, insertPersonStatement.program)
//                no need, because cascade deletion already removed these vehicles
//                deleteVehiclesByOwnerStatement.bind(owner.id)
//                brite.executeUpdateDelete(deleteVehiclesByOwnerStatement.table, deleteVehiclesByOwnerStatement.program)
                vehicles.forEach { (id, vin, ownerId, plateNumber, manufacturer, model, bodyType, color) ->
                    insertVehicleStatement.bind(id, vin, ownerId, plateNumber, manufacturer, model, bodyType, color)
                    brite.executeInsert(insertVehicleStatement.table, insertVehicleStatement.program)
                    brite.writableDatabase
                }
            }
            transaction.markSuccessful()
        } finally {
            transaction.end()
        }
    }

    override fun saveFollowingPeopleRemoveRest(people: List<Person>) {
        val transaction = brite.newTransaction()
        try {
            val deleteStatement = PersonModel.Delete_person_all(brite.writableDatabase)
            brite.executeUpdateDelete(deleteStatement.table, deleteStatement.program)
            val insetStatement = PersonModel.Insert_person(brite.writableDatabase)
            people.forEach { (id, firstName, lastName, phone) ->
                insetStatement.bind(id, firstName, lastName, phone)
                brite.executeInsert(insetStatement.table, insetStatement.program)
            }
            transaction.markSuccessful()
        } finally {
            transaction.end()
        }
    }
}