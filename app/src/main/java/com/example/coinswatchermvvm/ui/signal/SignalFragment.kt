package com.example.coinswatchermvvm.ui.signal

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.coinswatchermvvm.R
import com.example.coinswatchermvvm.utils.*
import kotlinx.android.synthetic.main.signal_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SignalFragment : Fragment()/*, KodeinAware*/ {

//    override val kodein by closestKodein()
//    private val viewModelFactory: SignalViewModelFactory by instance()
    private val LOG_TAG = "SignalFragment_debugLog_"

    companion object {
        fun newInstance() = SignalFragment()
    }

    private lateinit var viewModel: SignalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signal_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SignalViewModel::class.java)
        // TODO: Use the ViewModel

        if (getArguments() != null) {
            val argParam: Bundle? = getArguments()
            if(argParam != null) {

                tv_signal_symbol.text = "SIGNAL for ${argParam.getString(CURRENCY_SYMBOL)}"

                tv_signal_type.text = if(argParam.getBoolean(IS_STOP_LOSS_TYPE)) "Type: STOP LOSS" else "Type: TAKE PROFIT"

                //getActivity()!!.setTitle("SIGNAL for ${argParam.getString(CURRENCY_SYMBOL)}")


                //set color
                val color = if(argParam.getBoolean(IS_STOP_LOSS_TYPE)) Color.RED else Color.GREEN
                tv_signal_symbol.setBackgroundColor(color)
                tv_signal_type.setBackgroundColor(color)
                tv_signal_current_price.setBackgroundColor(color)
                tv_signal_price.setBackgroundColor(color)
                //

                button_delete_signal.setOnClickListener() {
                    Log.d(LOG_TAG,"CLICKED BTN DELETE SIGNAL")
                    viewModel.deleteSignal(argParam.getString(CURRENCY_SYMBOL))
                }

            }


        }

    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        if (getArguments() != null) {
//            val argParam: Bundle? = getArguments()
//            if(argParam != null) {
////                argParam.size()
//                Log.d(LOG_TAG,"FOUND ARGUMENTS, SIZE= ${argParam.size()}")
//                Log.d(LOG_TAG,"FOUND ARGUMENT, CURRENCY_SYMBOL: ${argParam.getString(CURRENCY_SYMBOL)}")
//                Log.d(LOG_TAG,"FOUND ARGUMENT, IS_STOP_LOSS_TYPE: ${argParam.getBoolean(
//                    IS_STOP_LOSS_TYPE
//                )}")
//                Log.d(LOG_TAG,"FOUND ARGUMENT, IS_USD_CURRENCY: ${argParam.getBoolean(
//                    IS_USD_CURRENCY
//                )}")
//
//                Log.d(LOG_TAG,"FOUND ARGUMENT, CURRENT_PRICE_BTC: ${argParam.getDouble(
//                    CURRENT_PRICE_BTC
//                )}")
//                Log.d(LOG_TAG,"FOUND ARGUMENT, CURRENT_PRICE_USD: ${argParam.getDouble(
//                    CURRENT_PRICE_USD
//                )}")
//                Log.d(LOG_TAG,"FOUND ARGUMENT, STOP_LOSS_BTC: ${argParam.getDouble(STOP_LOSS_BTC)}")
//                Log.d(LOG_TAG,"FOUND ARGUMENT, STOP_LOSS_USD: ${argParam.getDouble(STOP_LOSS_USD)}")
//                Log.d(LOG_TAG,"FOUND ARGUMENT, TAKE_PROFIT_BTC: ${argParam.getDouble(TAKE_PROFIT_BTC)}")
//                Log.d(LOG_TAG,"FOUND ARGUMENT, TAKE_PROFIT_USD: ${argParam.getDouble(TAKE_PROFIT_USD)}")
//
////                tv_signal.text = "SIGNAL for ${argParam.getString(CURRENCY_SYMBOL)}"
//
//                getActivity()!!.setTitle("SIGNAL for ${argParam.getString(CURRENCY_SYMBOL)}")
//            }
//        }
//
//    }

}
