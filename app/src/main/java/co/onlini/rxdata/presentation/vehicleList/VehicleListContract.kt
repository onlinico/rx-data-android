/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.presentation.vehicleList

import co.onlini.rxdata.data.model.FetchingError
import co.onlini.rxdata.data.model.Vehicle
import co.onlini.rxdata.presentation.BasePresenter

class VehicleListContract {
    interface View {
        fun applyVehicleList(vehicles: List<Vehicle>)
        fun showFetchingListProgressView()
        fun hideFetchingListProgressView()
        fun goToVehicleDetails(vehicle: Vehicle)
        fun showFetchingError(error: FetchingError)
    }

    interface Presenter : BasePresenter<View> {
        fun vehicleItemClicked(vehicle: Vehicle)
        fun requestDataFetching()
        fun onViewCreatedFirstTime()
    }
}

