/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.presentation.vehicleList

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import co.onlini.rxdata.App
import co.onlini.rxdata.R
import co.onlini.rxdata.data.model.FetchingError
import co.onlini.rxdata.data.model.Vehicle
import co.onlini.rxdata.presentation.BaseActivity
import co.onlini.rxdata.presentation.vehicleDetails.VehicleDetailsActivity
import kotlinx.android.synthetic.main.activity_vehicle_list.*
import javax.inject.Inject

class VehicleListActivity : VehicleListContract.View, BaseActivity() {
    @Inject
    internal lateinit var presenter: VehicleListContract.Presenter
    @Inject
    internal lateinit var listAdapter: VehicleListAdapter
    @Inject
    internal lateinit var listLayoutManager: RecyclerView.LayoutManager
    @Inject
    internal lateinit var itemDecorator: RecyclerView.ItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_list)
        title = getString(R.string.title_activity_vehicles_list)
        rvNotes.adapter = listAdapter
        rvNotes.layoutManager = listLayoutManager
        rvNotes.addItemDecoration(itemDecorator)
        layPullToRefresh.setOnRefreshListener {
            presenter.onViewCreatedFirstTime()
        }
        if (savedInstanceState == null) {
            presenter.onViewCreatedFirstTime()
        }
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
        DaggerVehicleListActivityComponent.builder()
                .appComponent(App.appComponent())
                .vehicleListModule(VehicleListModule(view = this, activityContext = this))
                .build()
                .inject(this)
    }

    override fun applyVehicleList(vehicles: List<Vehicle>) {
        listAdapter.notes = vehicles
    }

    override fun showFetchingListProgressView() {
        layPullToRefresh.isRefreshing = true
    }

    override fun hideFetchingListProgressView() {
        layPullToRefresh.isRefreshing = false
    }

    override fun goToVehicleDetails(vehicle: Vehicle) {
        val intent = VehicleDetailsActivity.getIntent(this, vehicle.id)
        startActivity(intent)
    }

    override fun showFetchingError(error: FetchingError) {
        val errorMessage = when (error) {
            FetchingError.UNAUTHORIZED -> getString(R.string.toast_note_fetching_error_unauthorized)
            FetchingError.CONNECTION_ISSUE -> getString(R.string.toast_note_fetching_error_connectivity_issue)
            FetchingError.INVALID_RESPONSE -> getString(R.string.toast_note_fetching_error_invalid_response)
            FetchingError.INTERNAL_ERROR -> getString(R.string.toast_note_fetching_error_internal_error)
            FetchingError.OTHER -> getString(R.string.toast_note_fetching_error)
        }
        toast(errorMessage)
    }

}
