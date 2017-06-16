/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.presentation.vehicleList

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import co.onlini.rxdata.data.VehicleDataFetcher
import co.onlini.rxdata.data.VehiclesRepo
import co.onlini.rxdata.presentation.PerActivity
import dagger.Module
import dagger.Provides

@Module
class VehicleListModule(private val view: VehicleListContract.View,
                        private val activityContext: Context) {

    @Provides
    @PerActivity
    fun provideNoteListPresenter(vehiclesRepo: VehiclesRepo, vehicleDataFetcher: VehicleDataFetcher): VehicleListContract.Presenter {
        return VehicleListPresenter(view, vehiclesRepo, vehicleDataFetcher)
    }

    @Provides
    @PerActivity
    fun provideNoteListAdapter(presenter: VehicleListContract.Presenter): VehicleListAdapter {
        return VehicleListAdapter(LayoutInflater.from(activityContext), presenter)
    }

    @Provides
    @PerActivity
    fun provideNoteListLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(activityContext, LinearLayoutManager.VERTICAL, false)
    }

    @Provides
    @PerActivity
    fun provideNoteListItemDecorator(): RecyclerView.ItemDecoration {
        return DividerItemDecoration(activityContext, DividerItemDecoration.VERTICAL)
    }
}