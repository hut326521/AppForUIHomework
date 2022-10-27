package com.example.appforuihomework

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class WeatherAdapter(
    private var context: Context, private var list: List<WeatherData>):
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>(){

    inner class ViewHolder(var dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_recycler, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding: ViewDataBinding = holder.dataBinding
        binding.setVariable(BR.item, list[position])
    }
}