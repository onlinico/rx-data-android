/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data.webapi

import co.onlini.rxdata.data.webapi.dto.AddVehicleDto
import co.onlini.rxdata.data.webapi.dto.VehicleDto
import co.onlini.rxdata.data.webapi.dto.VehicleOwnerDto
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface WebService {
    @GET("/owners")
    fun getVehicleOwners(): Observable<Response<List<VehicleOwnerDto>>>

    @POST("/owners/{ownerId}/vehicles")
    fun addVehicle(@Path("ownerId") ownerId: Long, @Body vehicle: AddVehicleDto): VehicleDto

    @PUT("/owners/{ownerId}/vehicles")
    fun modifyVehicle(@Path("ownerId") ownerId: Long, @Body vehicle: VehicleDto): VehicleDto

    @DELETE("/owners/{ownerId}/vehicles/{vehicleId}")
    fun removeVehicle(@Path("ownerId") ownerId: Long, @Path("vehicleId") vehicleId: Long): Long
}