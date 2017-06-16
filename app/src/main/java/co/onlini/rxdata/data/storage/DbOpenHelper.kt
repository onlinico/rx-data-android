/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data.storage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import co.onlini.rxdata.BuildConfig
import timber.log.Timber

class DbOpenHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        createTables(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Timber.i("Started upgrading database %s from version %s to %s.", db.path, oldVersion, newVersion)
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.setForeignKeyConstraintsEnabled(true)
    }

    private fun createTables(database: SQLiteDatabase) {
        database.apply {
            try {
                beginTransaction()
                execSQL(PersonModel.CREATE_TABLE)
                execSQL(VehicleModel.CREATE_TABLE)
                setTransactionSuccessful()
            } catch (e: Exception) {
                Timber.e("Error occurred during tables creation:", e)
            } finally {
                endTransaction()
            }
        }
    }

    companion object {
        private val DATABASE_NAME = BuildConfig.APPLICATION_ID + ".database"
        private val DATABASE_VERSION = 1
    }
}