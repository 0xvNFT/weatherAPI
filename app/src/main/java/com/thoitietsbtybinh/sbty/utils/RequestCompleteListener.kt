package com.thoitietsbtybinh.sbty.utils


interface RequestCompleteListener<T> {
    fun onRequestCompleted(data: T)
    fun onRequestFailed(errorMessage: String?)
}