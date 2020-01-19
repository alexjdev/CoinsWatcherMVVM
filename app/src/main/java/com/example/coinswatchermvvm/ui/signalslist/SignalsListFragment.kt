package com.example.coinswatchermvvm.ui.signalslist

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.coinswatchermvvm.R
import com.example.coinswatchermvvm.data.db.SignalEntry
import com.example.coinswatchermvvm.ui.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.signals_list_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

//class SignalsListFragment : Fragment() {
//
//    companion object {
//        fun newInstance() = SignalsListFragment()
//    }
//
//    private lateinit var viewModel: SignalsListViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.signals_list_fragment, container, false)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(SignalsListViewModel::class.java)
//        // TODO: Use the ViewModel
//    }
//
//}

class SignalsListFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: SignalsListViewModelFactory by instance()

    private lateinit var viewModel: SignalsListViewModel

    private val LOG_TAG = "SignalsListFragment_debugLog_"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signals_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SignalsListViewModel::class.java)

        bindUI()
    }


    private fun bindUI() = launch {
        val coinsList = viewModel.signalsList.await()

        coinsList.observe(this@SignalsListFragment, Observer {
            if (it == null) return@Observer

            initRecyclerView(it.toSignalsItems())

            signal_group_loading.visibility = View.GONE
        })


        button_delete_all_signals.setOnClickListener() {
            Log.d(LOG_TAG,"CLICKED BTN DELETE ALL SIGNALS")
            //Log.d(LOG_TAG,"Current signals in list: ${viewModel.getSignalsCount()}")
            viewModel.deleteAllSignals()

            //GoTo Main list or clear signals list
        }
    }



    private fun List<SignalEntry>.toSignalsItems() : List<SignalItem> {
        return this.map {
            SignalItem(it)
        }
    }

    private fun initRecyclerView(items: List<SignalItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView_signals.apply {
            layoutManager = LinearLayoutManager(this@SignalsListFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            (item as? SignalItem)?.let {
                //May be I'll use it (Edit signal)
                Log.d(LOG_TAG,"CLICKED SIGNAL: ${it.signalEntry.symbol}")
            }
        }
    }

//    private fun showCoinDetail(item: CoinItem, view: View) {
//
////        Log.d(LOG_TAG,"showWeatherDetail for: ${item.coinResponse.rank} ${item.coinResponse.name} ${view.toString()}")
//        val actionDetail = MainCoinsListFragmentDirections.actionDetail(item.coinResponse.rank)
//        Navigation.findNavController(view).navigate(actionDetail)
//    }

}