package com.example.appforuihomework

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.net.URL
import java.util.*
import kotlin.concurrent.timerTask

class MainViewModel : ViewModel() {
    var weatherList = MutableLiveData<List<WeatherData>>()
    var delay = 60 * 1000L

    fun startRetrieveData() {
        Timer().schedule(timerTask{
            getBikeDataFromServer()
        }, 0, delay) }

    private fun getBikeDataFromServer() {
        runBlocking {
            val tempList = mutableListOf<WeatherData>()
            val url = "https://data.epa.gov.tw/api/v2/aqx_p_432?limit=1000&api_key=cebebe84-e17d-4022-a28f-81097fda5896&sort=ImportDate%20desc&format=json"
            val weatherJsonStr = URL(url).readText()
            val weatherJsonArr = JSONObject(weatherJsonStr).getJSONArray("records")

            for (i in 0 until weatherJsonArr.length()) {
                val weatherObj = weatherJsonArr.getJSONObject(i)

                tempList.add(
                    WeatherData(
                        weatherObj.getString("siteid"),
                        weatherObj.getString("sitename"),
                        weatherObj.getString("county"),
                        weatherObj.getString("pm25"),
                        weatherObj.getString("status")
                    )
                )
            }

            weatherList.postValue(tempList)
        }
    }
}