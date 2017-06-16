/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.presentation.vehicleDetails

import co.onlini.rxdata.data.model.Vehicle
import co.onlini.rxdata.presentation.BasePresenter

class VehicleDetailsContract {
    interface View {
        fun applyVehicle(vehicle: Vehicle)
        fun showUnableToLoadVehicle()
    }

    interface Presenter : BasePresenter<View>
}

