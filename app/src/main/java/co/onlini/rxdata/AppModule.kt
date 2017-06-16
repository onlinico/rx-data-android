/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata

import android.app.Application
import dagger.Provides
import javax.inject.Singleton

@dagger.Module
class AppModule(internal val application: Application) {
    @Singleton
    @Provides
    fun getApplication(): Application {
        return application
    }
}
