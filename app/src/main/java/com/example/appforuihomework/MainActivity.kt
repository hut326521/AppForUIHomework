package com.example.appforuihomework

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by viewModels()
    private var mTopRecyclerView: RecyclerView? = null
    private var mBottomRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: change way to find ID
        mTopRecyclerView = findViewById(R.id.recycler_view_top)
        mBottomRecyclerView = findViewById(R.id.recycler_view_bottom)

        initView()
        initData()

        model.startRetrieveData()
    }

    private fun initView() {
        val layoutManagerTop = LinearLayoutManager(this)
        layoutManagerTop.orientation = LinearLayoutManager.HORIZONTAL
        mTopRecyclerView?.layoutManager = layoutManagerTop

        val layoutManagerBottom = LinearLayoutManager(this)
        layoutManagerBottom.orientation = LinearLayoutManager.VERTICAL
        mBottomRecyclerView?.layoutManager = layoutManagerBottom
    }

    private fun initData() {
        model.weatherGoodList.observe(this, Observer {
            mTopRecyclerView?.adapter = TopWeatherAdapter(this@MainActivity, it)
        })
        model.weatherBadList.observe(this, Observer {
            mBottomRecyclerView?.adapter = BottomWeatherAdapter(this@MainActivity, it)
        })
    }
}