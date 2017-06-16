/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.presentation.vehicleDetails

import co.onlini.rxdata.data.VehiclesRepo
import co.onlini.rxdata.presentation.PerActivity
import dagger.Module
import dagger.Provides

@Module
class VehicleDetailsModule(private val view: VehicleDetailsContract.View,
                           private val vehicleId: Long) {

    @Provides
    @PerActivity
    fun provideNoteListPresenter(vehiclesRepo: VehiclesRepo): VehicleDetailsContract.Presenter {
        return VehicleDetailsPresenter(vehicleId, view, vehiclesRepo)
    }
}