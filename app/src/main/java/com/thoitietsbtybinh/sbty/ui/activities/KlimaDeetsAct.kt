package com.thoitietsbtybinh.sbty.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thoitietsbtybinh.sbty.R
import com.thoitietsbtybinh.sbty.data.models.ResponseWeather
import com.thoitietsbtybinh.sbty.data.repository.remote.KlimaRepo
import com.thoitietsbtybinh.sbty.utils.Status
import com.thoitietsbtybinh.sbty.utils.unixTimestampToTimeString
import com.thoitietsbtybinh.sbty.viewmodel.MyViewModel
import kotlinx.android.synthetic.main.activity_weather_details.*
import kotlinx.android.synthetic.main.layout_additional_weather_info.*
import kotlinx.android.synthetic.main.layout_info.*

class KlimaDeetsAct : AppCompatActivity() {

    private lateinit var viewModel: MyViewModel
    private lateinit var weatherRepo: KlimaRepo
    private var cityID: String? = null
    private var lat: String? = null
    private var lon: String? = null
    private var city: String? = null

    companion object {
        const val CITY_ID = "city_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)

        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        weatherRepo = KlimaRepo()

        iv_add.setImageResource(R.drawable.back)
        iv_more.visibility = View.GONE

        cityID = intent.getStringExtra(CITY_ID)

        viewModel.getWeatherByCityID(weatherRepo, cityID!!)

        setUpObservers()

    }

    private fun setUpObservers() {
        viewModel.weatherByCityID.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        inc_info_weather.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        anim_failed.visibility = View.GONE
                        anim_network.visibility = View.GONE
                        setUpUI(it.data)
                    }

                    Status.ERROR -> {
                        showFailedView(it.message)
                    }

                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        anim_failed.visibility = View.GONE
                        anim_network.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun showFailedView(message: String?) {
        progressBar.visibility = View.GONE
        inc_info_weather.visibility = View.GONE

        when (message) {
            "Network Failure" -> {
                anim_failed.visibility = View.GONE
                anim_network.visibility = View.VISIBLE
            }

            else -> {
                anim_network.visibility = View.GONE
                anim_failed.visibility = View.VISIBLE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpUI(data: ResponseWeather?) {
        tv_temp.text = data?.main?.temp.toString()
        tv_city_name.text = data?.name
        tv_weather_condition.text = data?.weather!![0].main
        tv_sunrise_time.text = data.sys.sunrise.unixTimestampToTimeString()
        tv_sunset_time.text = data.sys.sunset.unixTimestampToTimeString()
        tv_real_feel_text.text =
            "${data.main.feelsLike}${getString(R.string.degree_celsius_symbol)}"
        tv_cloudiness_text.text = "${data.clouds.all}%"
        tv_wind_speed_text.text = "${data.wind.speed}m/s"
        tv_humidity_text.text = "${data.main.humidity}%"
        tv_pressure_text.text = "${data.main.pressure}hPa"
        tv_visibility_text.text = "${data.visibility}M"

        lat = data.coord.lat.toString()
        lon = data.coord.lon.toString()
        city = data.name
    }

    fun onAddButtonClicked(view: View) {
        onBackPressed()
        finish()
    }

    fun onForecastButtonClicked(view: View) {
        startActivity(
            Intent(this@KlimaDeetsAct, KlimaCastAct::class.java)
                .putExtra(KlimaCastAct.LATITUDE, lat)
                .putExtra(KlimaCastAct.LONGITUDE, lon)
                .putExtra(KlimaCastAct.CITY_NAME, city)
        )
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}