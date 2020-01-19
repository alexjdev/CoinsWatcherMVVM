package com.example.coinswatchermvvm.ui.addsignal

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.example.coinswatchermvvm.R
import com.example.coinswatchermvvm.data.db.SignalEntry
import com.example.coinswatchermvvm.databinding.AddEditSignalFragmentBinding
import com.example.coinswatchermvvm.ui.ScopedFragment
import kotlinx.android.synthetic.main.add_edit_signal_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory

//class AddEditSignalFragment : Fragment() {
//
//    companion object {
//        fun newInstance() = AddEditSignalFragment()
//    }
//
//    private lateinit var viewModel: AddEditSignalViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.add_edit_signal_fragment, container, false)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(AddEditSignalViewModel::class.java)
//        // TODO: Use the ViewModel
//    }
//
//}

class AddEditSignalFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactoryInstanceFactory
            : ((String) -> AddEditSignalViewModelFactory) by factory()

    private lateinit var viewModel: AddEditSignalViewModel

    private val LOG_TAG = "AddEditSignalFragment_debugLog_"

    lateinit var binding: AddEditSignalFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.add_edit_signal_fragment, container, false)


        binding = DataBindingUtil.inflate<AddEditSignalFragmentBinding>(inflater, R.layout.add_edit_signal_fragment, container, false)
        binding.setLifecycleOwner(this)


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { AddEditSignalFragmentArgs.fromBundle(it) }
        val symbol = safeArgs?.symbol

        viewModel = ViewModelProviders.of(this, viewModelFactoryInstanceFactory(symbol!!))
            .get(AddEditSignalViewModel::class.java)

        binding.viewmodel = viewModel

        bindUI()

        tvSymbol.text = symbol

    }


    private fun bindUI() = launch(Dispatchers.Main) {

        val signalEntry = viewModel.signalEntry.await()

        signalEntry.observe(this@AddEditSignalFragment, Observer {
            //Log.d(LOG_TAG,"bindUI---------")
            //Log.d(LOG_TAG,"bindUI signalEntry: ${signalEntry.value.toString()}")
            if (it == null) return@Observer

            Log.d(LOG_TAG,"bindUI signalEntry: ${it.toString()}")

            editText_usd_take_profit.text = Editable.Factory.getInstance().newEditable(signalEntry.value!!.priceUsdTakeProfit.toString())
            editText_usd_stop_loss.text = Editable.Factory.getInstance().newEditable(signalEntry.value!!.priceUsdStopLoss.toString())
            editText_btc_take_profit.text = Editable.Factory.getInstance().newEditable(signalEntry.value!!.priceBtcTakeProfit.toString())
            editText_btc_stop_loss.text = Editable.Factory.getInstance().newEditable(signalEntry.value!!.priceBtcStopLoss.toString())

        })

        button_save_signal.setOnClickListener() {

            //TODO: CHECK FIELDS BEFORE!!!
            if(checkFieldsBeforeSave()) {
                viewModel.onSaveSignal2("TEST_CLICKED")// TEST
                viewModel.onSaveSignal(SignalEntry(
                    tvSymbol.text.toString(),
                    editText_usd_take_profit.text.toString().toDouble(),
                    editText_usd_stop_loss.text.toString().toDouble(),
                    editText_btc_take_profit.text.toString().toDouble(),
                    editText_btc_stop_loss.text.toString().toDouble()
                ))
            }
        }


        button_remove_signal.setOnClickListener() {
            viewModel.onRemoveSignal(tvSymbol.text.toString())
        }

    }

    private fun checkFieldsBeforeSave(): Boolean {

        if(editText_usd_take_profit.text == null || (editText_usd_take_profit.text != null && editText_usd_take_profit.text.toString().equals("") ) ) {
            editText_usd_take_profit.text = Editable.Factory.getInstance().newEditable("0")
        }

        if(editText_usd_stop_loss.text == null || (editText_usd_stop_loss.text != null && editText_usd_stop_loss.text.toString().equals("") ) ) {
            editText_usd_stop_loss.text = Editable.Factory.getInstance().newEditable("0")
        }

        if(editText_btc_take_profit.text == null || (editText_btc_take_profit.text != null && editText_btc_take_profit.text.toString().equals("") ) ) {
            editText_btc_take_profit.text = Editable.Factory.getInstance().newEditable("0")
        }

//        Log.d(LOG_TAG,"AAAcheckFieldsBeforeSave_editText_btc_stop_loss: ${editText_btc_stop_loss.text}")
//        Log.d(LOG_TAG,"AAAcheckFieldsBeforeSave_editText_btc_stop_loss == NaN: ${ Double.NaN == editText_btc_stop_loss.text.toString().toDouble()}")
//        Log.d(LOG_TAG,"AAAcheckFieldsBeforeSave_editText_btc_stop_loss == 0.0: ${ 0.0 == editText_btc_stop_loss.text.toString().toDouble()}")

        if(editText_btc_stop_loss.text == null || (editText_btc_stop_loss.text != null && editText_btc_stop_loss.text.toString().equals("") ) ) {
            editText_btc_stop_loss.text = Editable.Factory.getInstance().newEditable("0")
            Log.d(LOG_TAG,"checkFieldsBeforeSave_editText_btc_stop_loss: ${editText_btc_stop_loss.text}")
            Log.d(LOG_TAG,"checkFieldsBeforeSave_editText_btc_stop_loss == NaN: ${ Double.NaN == editText_btc_stop_loss.text.toString().toDouble()}")
        }

        return true
    }

}
