/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data.webapi.dto

import co.onlini.rxdata.data.model.BodyTypeDto
import com.google.gson.annotations.SerializedName

class VehicleDto(@SerializedName("id") val id: Long,
                 @SerializedName("vin") val vin: String,
                 @SerializedName("plate_number") val plateNumber: String,
                 @SerializedName("manufacturer") val manufacturer: String,
                 @SerializedName("model") val model: String,
                 @SerializedName("body_type") val bodyType: BodyTypeDto,
                 @SerializedName("color") val color: Long)