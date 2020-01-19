package com.example.coinswatchermvvm.ui.maincoinslist

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.coinswatchermvvm.R
import com.example.coinswatchermvvm.data.response.CoinResponse
import com.example.coinswatchermvvm.ui.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.main_coins_list_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class MainCoinsListFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: MainCoinsListViewModelFactory by instance()

    private lateinit var viewModel: MainCoinsListViewModel

    private val LOG_TAG = "MainCoinsListFragment_debugLog_"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_coins_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainCoinsListViewModel::class.java)

        bindUI()
    }


    private fun bindUI() = launch {
        val coinsList = viewModel.coinsList.await()

        coinsList.observe(this@MainCoinsListFragment, Observer {
            if (it == null) return@Observer

//            ///
//            //UPDATE STRING:
//            var sb: StringBuilder = StringBuilder("")
//
//            val length = it.size
//            for (i in 0 until length) {
//                Log.d(LOG_TAG,"GOT COININFO: ${it!!.get(i).toString()}")
//                sb.append(it!!.get(i).rank)
//                sb.append(" ")
//                sb.append(it!!.get(i).name)
//                sb.append(" ")
//                sb.append(it!!.get(i).priceUsd)
//                sb.append("\r\n")
//            }
//            mainCoinsListFragment_textViev.text = sb.toString()
//            ///

            initRecyclerView(it.toCoinsItems())

            group_loading.visibility = View.GONE
        })
    }



    private fun List<CoinResponse>.toCoinsItems() : List<CoinItem> {
        return this.map {
            CoinItem(it)
        }
    }

    private fun initRecyclerView(items: List<CoinItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainCoinsListFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            (item as? CoinItem)?.let {
//                Toast.makeText(this@MainCoinsListFragment.context, "Clicked: ${item.coinResponse.rank} ${item.coinResponse.name} ${item.coinResponse.symbol}", Toast.LENGTH_SHORT).show()
                showCoinDetail(item, view)
            }
        }
    }

    private fun showCoinDetail(item: CoinItem, view: View) {

//        Log.d(LOG_TAG,"showWeatherDetail for: ${item.coinResponse.rank} ${item.coinResponse.name} ${view.toString()}")
        val actionDetail = MainCoinsListFragmentDirections.actionDetail(item.coinResponse.rank)
        Navigation.findNavController(view).navigate(actionDetail)
    }

}