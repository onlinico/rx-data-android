/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.presentation.vehicleDetails

import co.onlini.rxdata.AppComponent
import co.onlini.rxdata.presentation.PerActivity
import dagger.Component

@PerActivity
@Component(modules = arrayOf(VehicleDetailsModule::class), dependencies = arrayOf(AppComponent::class))
interface VehicleDetailsActivityComponent {
    fun inject(detailsActivity: VehicleDetailsActivity)
}