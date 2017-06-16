/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data.storage.typeAdapter

import co.onlini.rxdata.data.model.Vin
import com.squareup.sqldelight.ColumnAdapter

class VinAdapter : ColumnAdapter<Vin, String> {
    override fun encode(vin: Vin): String {
        return vin.value
    }

    override fun decode(databaseValue: String?): Vin {
        return Vin(databaseValue ?: throw IllegalStateException("Unable to decode Vin, database value is null"))
//         or
//        return Vin(databaseValue ?: "no value")
    }

    companion object {
        val INSTANCE by lazy { VinAdapter() }
    }
}