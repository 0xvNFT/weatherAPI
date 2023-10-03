package com.thoitietsbtybinh.sbty.data.repository.local

import com.thoitietsbtybinh.sbty.data.models.LokasyonD
import com.thoitietsbtybinh.sbty.utils.RequestCompleteListener

interface LokasyonProInt {
    fun getUserCurrentLocation(callback: RequestCompleteListener<LokasyonD>)
}





