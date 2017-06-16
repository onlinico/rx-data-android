/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data.model

import com.google.gson.annotations.SerializedName

enum class BodyTypeDto {
    @SerializedName("SUV")
    SUV,
    @SerializedName("SEDAN")
    SEDAN,
    @SerializedName("HATCHBACK")
    HATCHBACK,
    @SerializedName("COUPE")
    COUPE,
    @SerializedName("VAN")
    VAN,
    @SerializedName("PICKUP")
    PICKUP,
    @SerializedName("OTHER")
    OTHER,
    @SerializedName("UNKNOWN")
    UNKNOWN;

    fun toBodyType(): BodyType {
        return when (this) {
            BodyTypeDto.SUV -> BodyType.SUV
            BodyTypeDto.SEDAN -> BodyType.SEDAN
            BodyTypeDto.HATCHBACK -> BodyType.HATCHBACK
            BodyTypeDto.COUPE -> BodyType.COUPE
            BodyTypeDto.VAN -> BodyType.VAN
            BodyTypeDto.PICKUP -> BodyType.PICKUP
            BodyTypeDto.OTHER -> BodyType.OTHER
            BodyTypeDto.UNKNOWN -> BodyType.UNKNOWN
        }
    }
}
