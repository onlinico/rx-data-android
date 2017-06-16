/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data.model

import co.onlini.rxdata.data.storage.VehicleModel
import co.onlini.rxdata.data.storage.typeAdapter.BodyTypeAdapter
import co.onlini.rxdata.data.storage.typeAdapter.VinAdapter

data class Vehicle(val id: Long,
                   val vin: Vin,
                   val ownerId: Long,
                   val plateNumber: String,
                   val manufacturer: String,
                   val model: String,
                   val bodyType: BodyType,
                   val color: Long) : VehicleModel {
    override fun _id(): Long = id

    override fun vin(): Vin = vin

    override fun owner_id(): Long = ownerId

    override fun plate_number(): String = plateNumber

    override fun manufacturer(): String = manufacturer

    override fun model(): String = model

    override fun body_type(): BodyType = bodyType

    override fun color(): Long? = color

    companion object {
        private val CREATOR = VehicleModel.Creator { _id, vin, owner_id, plate_number, manufacturer, model, body_type, color ->
            Vehicle(_id, vin, owner_id, plate_number, manufacturer, model, body_type, color ?: 0L)
        }
        val FACTORY = VehicleModel.Factory(CREATOR, VinAdapter.INSTANCE, BodyTypeAdapter.INSTANCE)
        val MAPPER = VehicleModel.Mapper(FACTORY)
    }
}