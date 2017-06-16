/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.presentation.vehicleDetails

import android.content.Context
import android.content.Intent
import co.onlini.rxdata.App
import co.onlini.rxdata.R
import co.onlini.rxdata.data.model.Vehicle
import co.onlini.rxdata.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_vehicle_details.*
import javax.inject.Inject

class VehicleDetailsActivity : VehicleDetailsContract.View, BaseActivity() {

    companion object {
        private val EXTRA_VEHICLE_ID = "EXTRA_VEHICLE_ID"
        fun getIntent(context: Context, vehicleId: Long): Intent {
            val intent = Intent(context, VehicleDetailsActivity::class.java)
            intent.putExtra(EXTRA_VEHICLE_ID, vehicleId)
            return intent
        }
    }

    @Inject
    internal lateinit var presenter: VehicleDetailsContract.Presenter

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.title_activity_vehicle_details)
        setContentView(R.layout.activity_vehicle_details)
    }

    override fun onStart() {
        super.onStart()
        presenter.onViewStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onViewStop()
    }

    override fun setupComponent() {
        val vehicleId = intent.getLongExtra(EXTRA_VEHICLE_ID, -1)
        DaggerVehicleDetailsActivityComponent.builder()
                .appComponent(App.appComponent())
                .vehicleDetailsModule(VehicleDetailsModule(view = this, vehicleId = vehicleId))
                .build()
                .inject(this)
    }

    override fun applyVehicle(vehicle: Vehicle) {
        tvVehicleData.text = vehicle.toString()
    }

    override fun showUnableToLoadVehicle() {
        val message = getString(R.string.toast_unable_to_load_vehicle_data)
        tvVehicleData.text = message
        toast(message)
    }

}
