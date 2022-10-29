package com.example.appforuihomework

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.net.URL
import java.util.*
import kotlin.concurrent.timerTask

class MainViewModel : ViewModel() {
    var weatherGoodList = MutableLiveData<List<WeatherData>>()
    var weatherBadList = MutableLiveData<List<WeatherData>>()
    var allDataList = MutableLiveData<List<WeatherData>>()

    var delay = 60 * 1000L
    private val splitThreshold = 10

    fun startRetrieveData() {
        Timer().schedule(timerTask{
            getBikeDataFromServer()
        }, 0, delay) }

    private fun getBikeDataFromServer() {
        runBlocking {
            launch {
                val BigTempList = mutableListOf<WeatherData>()
                val SmallTempList = mutableListOf<WeatherData>()
                val AllTempList = mutableListOf<WeatherData>()
                val url = "https://data.epa.gov.tw/api/v2/aqx_p_432?limit=1000&api_key=cebebe84-e17d-4022-a28f-81097fda5896&sort=ImportDate%20desc&format=json"
                val weatherJsonStr = URL(url).readText()
                //Log.d("MikeTest", "weatherJson: $weatherJsonStr")
                val weatherJsonArr = JSONObject(weatherJsonStr).getJSONArray("records")


                for (i in 0 until weatherJsonArr.length()) {
                    val weatherObj = weatherJsonArr.getJSONObject(i)
                    val pm = weatherObj.getString("pm2.5").trim()
                    val status = weatherObj.getString("status").trim()
                    val isEvent: Boolean = (status == "良好")
                    val statusHint = "The status is good, we want to go out to have fun"


                    if(pm.isNotEmpty()) {
                        if(isEvent) {
                            AllTempList.add(
                                WeatherData(
                                    true,
                                    weatherObj.getString("siteid"),
                                    weatherObj.getString("sitename"),
                                    weatherObj.getString("county"),
                                    pm,
                                    statusHint
                                )
                            )
                        } else {
                            AllTempList.add(
                                WeatherData(
                                    false,
                                    weatherObj.getString("siteid"),
                                    weatherObj.getString("sitename"),
                                    weatherObj.getString("county"),
                                    pm,
                                    status
                                )
                            )
                        }

                        if(pm.toInt() > splitThreshold) {
                            if(isEvent) {
                                BigTempList.add(
                                    WeatherData(
                                        true,
                                        weatherObj.getString("siteid"),
                                        weatherObj.getString("sitename"),
                                        weatherObj.getString("county"),
                                        pm,
                                        statusHint
                                    )
                                )
                            } else {
                                BigTempList.add(
                                    WeatherData(
                                        false,
                                        weatherObj.getString("siteid"),
                                        weatherObj.getString("sitename"),
                                        weatherObj.getString("county"),
                                        pm,
                                        status
                                    )
                                )
                            }
                        } else {
                            SmallTempList.add(
                                WeatherData(
                                    false,
                                    weatherObj.getString("siteid"),
                                    weatherObj.getString("sitename"),
                                    weatherObj.getString("county"),
                                    pm,
                                    weatherObj.getString("status")
                                )
                            )
                        }
                    }
                }

                weatherGoodList.postValue(SmallTempList)
                weatherBadList.postValue(BigTempList)
                allDataList.postValue(AllTempList)
            }
        }
    }
}