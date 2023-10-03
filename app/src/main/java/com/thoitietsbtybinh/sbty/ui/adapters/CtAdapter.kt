package com.thoitietsbtybinh.sbty.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.thoitietsbtybinh.sbty.R
import com.thoitietsbtybinh.sbty.data.models.Ct
import com.thoitietsbtybinh.sbty.utils.DiffUtilCallback
import kotlinx.android.synthetic.main.item_cities.view.*

class CtAdapter : RecyclerView.Adapter<CtAdapter.Holder>() {
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName = itemView.tv_city_name!!
        val countryName = itemView.tv_country_name!!
        val addBtn = itemView.iv_add_city!!
        val addedTV = itemView.tv_added!!
    }

    val differ = AsyncListDiffer(this, DiffUtilCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cities, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val cities = differ.currentList[position]
        bindData(holder, cities)
    }

    private fun bindData(holder: Holder, ct: Ct?) {
        holder.apply {
            cityName.text = ct?.name
            countryName.text = ct?.country
            if (ct?.isSaved == 1) {
                addBtn.visibility = View.GONE
                addedTV.visibility = View.VISIBLE
            } else {
                addedTV.visibility = View.GONE
                addBtn.visibility = View.VISIBLE
            }
            addBtn.setOnClickListener {
                onItemClickListener?.let { it(ct!!) }
                addedTV.visibility = View.VISIBLE
                addBtn.visibility = View.GONE
            }
            itemView.setOnClickListener {
                onParentItemClickListener?.let { it(ct!!) }
            }
        }
    }

    private var onItemClickListener: ((Ct) -> Unit)? = null
    private var onParentItemClickListener: ((Ct) -> Unit)? = null

    fun setOnItemClickListener(listener: (Ct) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnParentClickListener(listener: (Ct) -> Unit) {
        onParentItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}