package com.example.appforuihomework

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appforuihomework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by viewModels()
    private var mTopRecyclerView: RecyclerView? = null
    private var mBottomRecyclerView: RecyclerView? = null
    private var mSearchRecyclerView: RecyclerView? = null
    private var mHomeTitle: TextView? = null
    private var mSearchButton: TextView? = null
    private var mSplitLine2: View? = null
    private var mBackButton: TextView? = null
    private var mSearchEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setVariable(BR.clickable, this)

        mTopRecyclerView = findViewById(R.id.recycler_view_top)
        mBottomRecyclerView = findViewById(R.id.recycler_view_bottom)
        mSearchRecyclerView = findViewById(R.id.search_recview)
        mHomeTitle = findViewById(R.id.AppTitle)
        mSearchButton = findViewById(R.id.Search_Button)
        mSplitLine2 = findViewById(R.id.splitLine2)
        mBackButton = findViewById(R.id.BackButton)
        mSearchEditText = findViewById(R.id.Search_Edit_text)

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

        val layoutManagerSearch = LinearLayoutManager(this)
        layoutManagerSearch.orientation = LinearLayoutManager.VERTICAL
        mSearchRecyclerView?.layoutManager = layoutManagerSearch
    }

    private fun initData() {
        model.weatherGoodList.observe(this, Observer {
            mTopRecyclerView?.adapter = TopWeatherAdapter(this@MainActivity, it)
        })
        model.weatherBadList.observe(this, Observer {
            mBottomRecyclerView?.adapter = BottomWeatherAdapter(this@MainActivity, it)
        })
        model.allDataList.observe(this, Observer {
            mSearchRecyclerView?.adapter = SearchWeatherAdapter(this@MainActivity, it)
        })
    }

    fun toSearchPage() {
        mTopRecyclerView?.visibility = View.GONE
        mBottomRecyclerView?.visibility = View.GONE
        mSplitLine2?.visibility = View.GONE
        mSearchButton?.visibility = View.GONE
        mHomeTitle?.visibility = View.GONE

        mSearchRecyclerView?.visibility = View.VISIBLE
        mSearchEditText?.visibility = View.VISIBLE
        mBackButton?.visibility = View.VISIBLE
    }

    fun toHomePage() {
        mTopRecyclerView?.visibility = View.VISIBLE
        mBottomRecyclerView?.visibility = View.VISIBLE
        mSplitLine2?.visibility = View.VISIBLE
        mSearchButton?.visibility = View.VISIBLE
        mHomeTitle?.visibility = View.VISIBLE

        mSearchRecyclerView?.visibility = View.GONE
        mSearchEditText?.visibility = View.GONE
        mBackButton?.visibility = View.GONE
    }
}