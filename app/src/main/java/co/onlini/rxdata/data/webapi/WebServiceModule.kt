/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data.webapi

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class WebServiceModule {

    @Singleton
    @Provides
    fun getWebServiceMock(): WebService {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://example.com")
                .build()
        val behavior = NetworkBehavior.create()
        behavior.setDelay(2500, TimeUnit.MILLISECONDS)
        behavior.setFailurePercent(20)

        val mockRetrofit = MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior).build()
        val delegate = mockRetrofit.create(WebService::class.java)
        return MockWebService(delegate)
    }
}
