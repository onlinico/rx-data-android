/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data.model

import co.onlini.rxdata.data.storage.VehicleModel

data class PlateNumberOwnerName(val firstName: String, val lastName: String, val plateNumber: String) : VehicleModel.Select_vehicle_plate_number_and_owner_nameModel {
    override fun first_name(): String = firstName

    override fun last_name(): String = lastName

    override fun plate_number(): String = plateNumber

    companion object {
        val CREATOR = VehicleModel.Select_vehicle_plate_number_and_owner_nameCreator { first_name, last_name, plate_number ->
            PlateNumberOwnerName(first_name, last_name, plate_number)
        }

        val MAPPER = VehicleModel.Select_vehicle_plate_number_and_owner_nameMapper(CREATOR)
    }
}