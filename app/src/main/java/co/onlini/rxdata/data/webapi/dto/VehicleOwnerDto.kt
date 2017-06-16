/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data.webapi.dto

import com.google.gson.annotations.SerializedName

class VehicleOwnerDto(@SerializedName("id") val id: Long,
                      @SerializedName("first_name") val firstName: String,
                      @SerializedName("last_name") val lastName: String,
                      @SerializedName("phone") val phone: String?,
                      @SerializedName("vehicles") val vehicles: Collection<VehicleDto>)