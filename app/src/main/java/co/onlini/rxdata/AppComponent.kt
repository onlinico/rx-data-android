/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata

import android.app.Application
import co.onlini.rxdata.data.DomainModule
import co.onlini.rxdata.data.VehicleDataFetcher
import co.onlini.rxdata.data.VehiclesRepo
import co.onlini.rxdata.data.storage.StorageModule
import co.onlini.rxdata.data.webapi.WebServiceModule
import dagger.Component
import javax.inject.Singleton

/**
 * rxdata
 * Created by Rostyslav Yeromchenko on 23.05.17
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, DomainModule::class,
        WebServiceModule::class, StorageModule::class))
interface AppComponent {
    fun getVehiclesRepo(): VehiclesRepo
    fun getVehicleDataFetcher(): VehicleDataFetcher
    fun getApp(): Application
}