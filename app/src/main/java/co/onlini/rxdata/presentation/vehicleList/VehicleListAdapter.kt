/*
 * Copyright (C) 2017 ONLINICO OU
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package co.onlini.rxdata.presentation.vehicleList

import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.onlini.rxdata.R
import co.onlini.rxdata.data.model.Vehicle

class VehicleListAdapter(private val inflater: LayoutInflater,
                         internal val presenter: VehicleListContract.Presenter) : RecyclerView.Adapter<VehicleListAdapter.NoteViewHolder>() {
    var notes: List<Vehicle> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: NoteViewHolder?, position: Int) {
        val note = notes[position]
        holder?.bind(note)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NoteViewHolder {
        val view = inflater.inflate(R.layout.item_vehicle, parent, false)
        return NoteViewHolder(view, onClickListener)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    val onClickListener = fun(position: Int) {
        presenter.vehicleItemClicked(notes[position])
    }

    class NoteViewHolder(itemView: View, itemClickListener: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                itemClickListener(adapterPosition)
            }
        }

        val tvTitle: AppCompatTextView = itemView.findViewById(R.id.tvManufacturer)
        val tvDescription: AppCompatTextView = itemView.findViewById(R.id.tvModel)

        fun bind(vehicle: Vehicle) {
            tvTitle.text = vehicle.manufacturer
            tvDescription.text = vehicle.model
        }
    }
}