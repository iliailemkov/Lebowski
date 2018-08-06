package com.yury.lebowski.repository

import com.yury.lebowski.models.CurrencyType
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class RateRepository {

    // TODO: return last retrieved rate, saved into db
    fun getLastOfflineRate(currencyType: CurrencyType): Double {
        return when (currencyType) {
            CurrencyType.Dollar -> 1.0
            CurrencyType.Ruble -> 63.45
        }
    }

    // TODO: move to service and use Retrofit
    fun getOnlineRate(direction: String): Double {
        var rate: Double = 0.0
        val client = OkHttpClient()
        val url = "http://free.currencyconverterapi.com/api/v6/convert?q=$direction&compact=ultra"
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onResponse(call: Call?, response: Response?) {
                response?.body()?.string()?.let {
                    val data = JSONObject(it)
                    rate = data.getDouble("USD_RUB")
                }
            }

        })
        return rate
    }

}