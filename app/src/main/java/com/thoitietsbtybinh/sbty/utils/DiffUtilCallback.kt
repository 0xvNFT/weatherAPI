package com.thoitietsbtybinh.sbty.utils

import androidx.recyclerview.widget.DiffUtil
import com.thoitietsbtybinh.sbty.data.models.Ct
import com.thoitietsbtybinh.sbty.data.models.Daily

class DiffUtilCallback : DiffUtil.ItemCallback<Ct>() {
    override fun areItemsTheSame(oldItem: Ct, newItem: Ct): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Ct, newItem: Ct): Boolean {
        return oldItem == newItem
    }
}

class DiffUtilCallbackForecast : DiffUtil.ItemCallback<Daily>() {
    override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem == newItem
    }

}