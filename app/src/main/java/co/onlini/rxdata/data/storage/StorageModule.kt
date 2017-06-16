/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data.storage

import android.app.Application
import android.database.sqlite.SQLiteOpenHelper
import com.squareup.sqlbrite2.BriteDatabase
import com.squareup.sqlbrite2.SqlBrite
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton


@Module
class StorageModule {

    @Singleton
    @Provides
    fun getVehicleDao(brite: BriteDatabase): Dao {
        return DaoImpl(brite)
    }

    @Provides
    @Singleton
    internal fun getBriteDatabase(sqLiteOpenHelper: SQLiteOpenHelper): BriteDatabase {
        val sqlBrite = SqlBrite.Builder().build()
        return sqlBrite.wrapDatabaseHelper(sqLiteOpenHelper, Schedulers.io())
    }

    @Provides
    @Singleton
    internal fun getSQLiteOpenHelper(application: Application): SQLiteOpenHelper {
        return DbOpenHelper(application)
    }
}
