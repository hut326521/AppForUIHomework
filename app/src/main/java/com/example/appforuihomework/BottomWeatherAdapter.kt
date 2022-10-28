package com.example.appforuihomework

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


class BottomWeatherAdapter(
    private var context: Context, private var list: List<WeatherData>): RecyclerView.Adapter<BottomWeatherAdapter.ViewHolder>(){
    inner class ViewHolder(var dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutNormal: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_recycler, parent, false
        )

        val layoutEvent: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_recycler_event, parent, false
        )
        layoutEvent.setVariable(BR.clickable, this)

        return when (viewType) {
            1->{
                ViewHolder(layoutNormal)
            }
            2->{
                ViewHolder(layoutEvent)
            }
            else->{
                ViewHolder(layoutNormal)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding: ViewDataBinding = holder.dataBinding
        binding.setVariable(BR.item, list[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if(list[position].isEvent) 2 else 1
    }

    fun onEventClick() {
        Toast.makeText(context, "Hi UI!", Toast.LENGTH_SHORT).show()
    }
}