package com.thoitietsbtybinh.sbty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoitietsbtybinh.sbty.data.models.Ct
import com.thoitietsbtybinh.sbty.data.models.Ctu
import com.thoitietsbtybinh.sbty.data.models.LokasyonD
import com.thoitietsbtybinh.sbty.data.models.ResponseWeather
import com.thoitietsbtybinh.sbty.data.models.ResponseWeatherForecast
import com.thoitietsbtybinh.sbty.data.repository.local.CtRepo
import com.thoitietsbtybinh.sbty.data.repository.local.LokasyonPro
import com.thoitietsbtybinh.sbty.data.repository.remote.KlimaRepo
import com.thoitietsbtybinh.sbty.utils.RequestCompleteListener
import com.thoitietsbtybinh.sbty.utils.Resource
import com.thoitietsbtybinh.sbty.utils.error
import com.thoitietsbtybinh.sbty.utils.info
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class MyViewModel : ViewModel() {

    private val tag = "ViewModel"


    val locationLiveData = MutableLiveData<LokasyonD>()
    val locationLiveDataFailure = MutableLiveData<String>()

    val weatherByLocation = MutableLiveData<Resource<ResponseWeather>>()

    val cityByQuery = MutableLiveData<Resource<List<Ct>>>()

    val weatherByCityID = MutableLiveData<Resource<ResponseWeather>>()

    val weatherForecast = MutableLiveData<Resource<ResponseWeatherForecast>>()

    fun getCurrentLocation(model: LokasyonPro) {
        model.getUserCurrentLocation(object : RequestCompleteListener<LokasyonD> {
            override fun onRequestCompleted(data: LokasyonD) {
                locationLiveData.postValue(data)
            }

            override fun onRequestFailed(errorMessage: String?) {
                locationLiveDataFailure.postValue(errorMessage)
            }
        })
    }

    /**
     * Weather by Location call
     */
    fun getWeatherByLocation(model: KlimaRepo, lat: String, lon: String) {
        viewModelScope.launch { safeWeatherByLocationFetch(model, lat, lon) }
    }

    private suspend fun safeWeatherByLocationFetch(model: KlimaRepo, lat: String, lon: String) {
        weatherByLocation.postValue(Resource.loading(null))
        try {
            val response = model.getWeatherByLocation(lat, lon)
            weatherByLocation.postValue(handleWeatherResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> weatherByLocation.postValue(
                    Resource.error(
                        null,
                        "Network Failure"
                    )
                )

                else -> weatherByLocation.postValue(Resource.error(null, t.localizedMessage))
            }
        }
    }


    fun getWeatherByCityID(model: KlimaRepo, id: String) {
        viewModelScope.launch { safeWeatherByCityIDFetch(model, id) }
    }

    private suspend fun safeWeatherByCityIDFetch(model: KlimaRepo, id: String) {
        weatherByCityID.postValue(Resource.loading(null))
        try {
            val response = model.getWeatherByCityID(id)
            weatherByCityID.postValue(handleWeatherResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> weatherByCityID.postValue(Resource.error(null, "Network Failure"))
                else -> weatherByCityID.postValue(Resource.error(null, t.localizedMessage))
            }
        }
    }

    private fun handleWeatherResponse(response: Response<ResponseWeather>): Resource<ResponseWeather> {
        return if (response.isSuccessful) Resource.success(response.body()) else Resource.error(
            null,
            "Error: ${response.errorBody()}"
        )
    }

    fun getWeatherForecast(model: KlimaRepo, lat: String, lon: String, exclude: String) {
        viewModelScope.launch { safeWeatherForecastFetch(model, lat, lon, exclude) }
    }

    private suspend fun safeWeatherForecastFetch(
        model: KlimaRepo,
        lat: String,
        lon: String,
        exclude: String
    ) {
        weatherForecast.postValue(Resource.loading(null))
        try {
            val response = model.getWeatherForecast(lat, lon, exclude)
            weatherForecast.postValue(handleWeatherForecast(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> weatherForecast.postValue(Resource.error(null, "Network Failure"))
                else -> weatherForecast.postValue(Resource.error(null, t.localizedMessage))
            }
        }
    }

    private fun handleWeatherForecast(response: Response<ResponseWeatherForecast>): Resource<ResponseWeatherForecast> {
        return if (response.isSuccessful) Resource.success(response.body()) else Resource.error(
            null,
            "Error: ${response.errorBody()}"
        )
    }

    fun getCityByQuery(model: CtRepo, query: String) =
        viewModelScope.launch { safeCityByQueryFetch(model, query) }

    private suspend fun safeCityByQueryFetch(model: CtRepo, query: String) {
        cityByQuery.postValue(Resource.loading(null))
        try {
            val response = model.searchCities(key = query)
            cityByQuery.postValue(handleCitySearch(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> cityByQuery.postValue(Resource.error(null, "Network Failure"))
                else -> {
                    cityByQuery.postValue(Resource.error(null, t.localizedMessage))
                    error(tag, t.localizedMessage!!)
                }
            }
        }
    }

    private fun handleCitySearch(response: List<Ct>): Resource<List<Ct>> =
        Resource.success(response)

    fun updateSavedCities(model: CtRepo, obj: Ctu) = viewModelScope.launch {
        try {
            val info = model.updateSavedCities(obj)
            info(tag, "Success: Updating City DB: $info")
        } catch (e: Exception) {
            e.stackTrace
            error(tag, "Error: Updating City DB: ${e.localizedMessage}")
        }
    }

    fun getSavedCities(model: CtRepo, key: Int) = model.getSavedCities(key)


    fun deleteSavedCities(model: CtRepo, ct: Ct) = viewModelScope.launch {
        try {
            model.deleteSavedCities(ct)
        } catch (e: Exception) {
            e.stackTrace
            error(tag, "Error: Deleting City DB: ${e.localizedMessage}")
        }
    }


}