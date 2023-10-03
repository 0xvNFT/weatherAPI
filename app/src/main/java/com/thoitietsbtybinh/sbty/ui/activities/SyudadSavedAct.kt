package com.thoitietsbtybinh.sbty.ui.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.thoitietsbtybinh.sbty.R
import com.thoitietsbtybinh.sbty.data.models.Ctu
import com.thoitietsbtybinh.sbty.data.repository.local.CtRepo
import com.thoitietsbtybinh.sbty.db.CityDatabase
import com.thoitietsbtybinh.sbty.ui.adapters.SyudadSaveAdapter
import com.thoitietsbtybinh.sbty.utils.RecyclerItemTouchHelper
import com.thoitietsbtybinh.sbty.utils.lightStatusBar
import com.thoitietsbtybinh.sbty.viewmodel.MyViewModel
import kotlinx.android.synthetic.main.activity_saved_city.*

class SyudadSavedAct : AppCompatActivity(),
    RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private lateinit var viewModel: MyViewModel
    private lateinit var repository: CtRepo
    private lateinit var mAdapter: SyudadSaveAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = resources.getColor(android.R.color.white)
        lightStatusBar(this, true)
        setContentView(R.layout.activity_saved_city)

        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        repository = CtRepo(CityDatabase(this))
        mAdapter = SyudadSaveAdapter()

        setUpRecyclerView()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.getSavedCities(repository, 1).observe(this) { cities ->
            mAdapter.differ.submitList(cities)
        }
    }

    private fun setUpRecyclerView() {
        rv_saved_city.apply {
            layoutManager = LinearLayoutManager(this@SyudadSavedAct)
            setHasFixedSize(true)
            adapter = mAdapter
        }

        ItemTouchHelper(RecyclerItemTouchHelper(this@SyudadSavedAct)).attachToRecyclerView(
            rv_saved_city
        )

        mAdapter.setOnItemClickListener {
            startActivity(
                Intent(this@SyudadSavedAct, KlimaDeetsAct::class.java).putExtra(
                    KlimaDeetsAct.CITY_ID,
                    it.id.toString()
                )
            )
        }
    }

    fun onSearchTextClicked(view: View) {
        val intent = Intent(this@SyudadSavedAct, HanaAct::class.java)
        val options = ActivityOptions.makeSceneTransitionAnimation(
            this,
            Pair.create(tv_city_search, getString(R.string.label_search_hint))
        )
        startActivity(intent, options.toBundle())
        Handler(Looper.myLooper()!!).postDelayed({ finish() }, 1000)
    }

    fun onBackButtonClicked(view: View) {
        onBackPressed()
        finish()
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        if (viewHolder is SyudadSaveAdapter.Holder) {
            val pos = viewHolder.adapterPosition
            val cities = mAdapter.differ.currentList[pos]
            viewModel.updateSavedCities(
                CtRepo(CityDatabase(this@SyudadSavedAct)),
                Ctu(cities.id, 0)
            )

            Snackbar.make(cl_parent, "City removed from saved items", Snackbar.LENGTH_LONG).apply {
                setAction("Undo") {
                    viewModel.updateSavedCities(
                        CtRepo(CityDatabase(this@SyudadSavedAct)),
                        Ctu(cities.id, 1)
                    )
                }
                setBackgroundTint(resources.getColor(R.color.colorPrimary))
                setActionTextColor(resources.getColor(R.color.color_grey))
                show()
            }

        }

    }

}