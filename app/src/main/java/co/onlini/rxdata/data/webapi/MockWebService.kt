/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.data.webapi

import co.onlini.rxdata.data.model.BodyTypeDto
import co.onlini.rxdata.data.webapi.dto.AddVehicleDto
import co.onlini.rxdata.data.webapi.dto.VehicleDto
import co.onlini.rxdata.data.webapi.dto.VehicleOwnerDto
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.Calls
import java.util.concurrent.atomic.AtomicLong

class MockWebService(private val delegate: BehaviorDelegate<WebService>) : WebService {

    override fun getVehicleOwners(): Observable<Response<List<VehicleOwnerDto>>> {
        val vehicles = owners.firstOrNull()?.vehicles as? MutableList
        vehicles?.add(createVehicle())
        return delegate.returningResponse(owners).getVehicleOwners()
    }

    private fun createVehicle(): VehicleDto {
        val id = nextVehicleId()
        return VehicleDto(id, "vin_number_$id", "plate_$id", "manufacturer_$id", "model_$id", BodyTypeDto.COUPE, 0xFF00FF)
    }

    override fun addVehicle(ownerId: Long, vehicle: AddVehicleDto): VehicleDto {
        val vehicles = owners.find { it.id == ownerId }?.vehicles as? MutableList
        return if (vehicles != null) {
            val addedVehicle = VehicleDto(nextVehicleId(),
                    vehicle.vin,
                    vehicle.plateNumber,
                    vehicle.manufacturer,
                    vehicle.model,
                    vehicle.bodyType,
                    vehicle.color)
            vehicles.add(addedVehicle)
            delegate.returning(Calls.response(addedVehicle)).addVehicle(ownerId, vehicle)
        } else {
            delegate.returningResponse(createErrorResponseCall<VehicleDto>(404, "no such owner")).addVehicle(ownerId, vehicle)
        }
    }

    override fun modifyVehicle(ownerId: Long, vehicle: VehicleDto): VehicleDto {
        val vehicles = owners.find { it.id == ownerId }?.vehicles as? MutableList
        return if (vehicles != null) {
            val isRemoved = vehicles.removeIf { it.id == vehicle.id }
            if (isRemoved) {
                vehicles.add(vehicle)
                delegate.returning(Calls.response(vehicle)).modifyVehicle(ownerId, vehicle)
            } else {
                delegate.returningResponse(createErrorResponseCall<VehicleDto>(404, "no such vehicle")).modifyVehicle(ownerId, vehicle)
            }
        } else {
            delegate.returningResponse(createErrorResponseCall<VehicleDto>(404, "no such owner")).modifyVehicle(ownerId, vehicle)
        }
    }

    override fun removeVehicle(ownerId: Long, vehicleId: Long): Long {
        val vehicles = owners.find { it.id == ownerId }?.vehicles as? MutableList
        return if (vehicles != null) {
            val isRemoved = vehicles.removeIf { it.id == vehicleId }
            if (isRemoved) {
                delegate.returning(Calls.response(vehicleId)).removeVehicle(ownerId, vehicleId)
            } else {
                delegate.returningResponse(createErrorResponseCall<VehicleDto>(404, "no such vehicle")).removeVehicle(ownerId, vehicleId)
            }
        } else {
            delegate.returningResponse(createErrorResponseCall<VehicleDto>(404, "no such owner")).removeVehicle(ownerId, vehicleId)
        }
    }

    fun <T> createErrorResponseCall(code: Int, message: String): retrofit2.Call<T> {
        val errorBody = ResponseBody.create(MediaType.parse("text/json"), message)
        val response = Response.error<T>(code, errorBody)
        return Calls.response(response)
    }

    private val vehicleMaxId = AtomicLong(1L)
    private fun nextVehicleId() = vehicleMaxId.getAndIncrement()

    private val ownerMaxId = AtomicLong(1L)
    private fun nextOwnerId() = ownerMaxId.getAndIncrement()

    val owners = mutableListOf(
            VehicleOwnerDto(nextOwnerId(), "First_name_1", "Last_name_1", "Phone_1", mutableListOf(
                    createVehicle(),
                    createVehicle()
            )),
            VehicleOwnerDto(nextOwnerId(), "First_name_2", "Last_name_2", "Phone_2", mutableListOf(
                    createVehicle(),
                    createVehicle(),
                    createVehicle()
            )))
}