/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data.storage.typeAdapter

import co.onlini.rxdata.data.model.BodyType
import com.squareup.sqldelight.ColumnAdapter

class BodyTypeAdapter : ColumnAdapter<BodyType, Long> {
    override fun encode(enumValue: BodyType): Long {
        return enumValue.ordinal.toLong()
    }

    override fun decode(databaseValue: Long?): BodyType {
        return BodyType.values()[databaseValue?.toInt() ?: BodyType.UNKNOWN.ordinal]
    }

    companion object {
        val INSTANCE by lazy { BodyTypeAdapter() }
    }
}