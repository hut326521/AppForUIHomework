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


class SearchWeatherAdapter(
    private var context: Context, private var list: List<WeatherData>): RecyclerView.Adapter<SearchWeatherAdapter.ViewHolder>(){
    inner class ViewHolder(var dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutEvent: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.search_item_recycler_event, parent, false
        )
        layoutEvent.setVariable(BR.clickable_search, this)

        return ViewHolder(layoutEvent)
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