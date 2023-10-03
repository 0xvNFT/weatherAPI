package com.thoitietsbtybinh.sbty.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.thoitietsbtybinh.sbty.R
import com.thoitietsbtybinh.sbty.data.models.Ct
import com.thoitietsbtybinh.sbty.utils.DiffUtilCallback
import kotlinx.android.synthetic.main.item_saved_city.view.*


class SyudadSaveAdapter : RecyclerView.Adapter<SyudadSaveAdapter.Holder>() {

    val differ = AsyncListDiffer(this, DiffUtilCallback())
    private var onItemClickListener: ((Ct) -> Unit)? = null

    fun setOnItemClickListener(listener: (Ct) -> Unit) {
        onItemClickListener = listener
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName = itemView.tv_city_name_search!!
        val countryName = itemView.tv_country_name_search!!
        val temperature = itemView.tv_city_temp!!
        val foregroundView = itemView.view_foreground!!
        val backgroundView = itemView.view_background!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_saved_city, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val cities = differ.currentList[position]
        bindData(cities, holder)
    }

    private fun bindData(ct: Ct?, holder: Holder) {
        holder.apply {
            cityName.text = ct?.name
            countryName.text = ct?.country
            temperature.text = ""
            itemView.setOnClickListener { onItemClickListener?.let { it(ct!!) } }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}