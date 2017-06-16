/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data

import co.onlini.rxdata.data.storage.Dao
import co.onlini.rxdata.data.webapi.WebService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun getVehiclesRepo(dao: Dao): VehiclesRepo {
        return VehiclesRepoImpl(dao)
    }

    @Singleton
    @Provides
    fun getVehicleDataFetcher(dao: Dao, webService: WebService): VehicleDataFetcher {
        return VehicleDataFetcherImpl(dao, webService)
    }
}
