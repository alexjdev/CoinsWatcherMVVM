package com.example.coinswatchermvvm.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.coinswatchermvvm.R
import com.example.coinswatchermvvm.databinding.FragmentSettingsBinding
import com.example.coinswatchermvvm.ui.ScopedFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SettingsFragment: ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: SettingsViewModelFactory by instance()

    private lateinit var viewModel: SettingsViewModel

    private val LOG_TAG = "SettingsFragment_debugLog_"

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

//        DataBindingUtil.inflate(
//            inflater,
//            R.layout.fragment_settings,
//            container,
//            false
//        )


        binding = DataBindingUtil.inflate<FragmentSettingsBinding>(inflater, R.layout.fragment_settings, container, false) // https://medium.com/databinding-ktx/databindingutil-vs-databinding-ktx-5c0c04a5c483
        binding.setLifecycleOwner(this)


        return binding.root

        //binding = FragmentSettingsBinding.inflate(inflater, container, false)
        //return binding.root

        //return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SettingsViewModel::class.java)

        binding.viewmodel = viewModel //    https://www.youtube.com/watch?v=CxZB9yO8OnA //9. Android Data Binding
        Log.d(LOG_TAG,"onActivityCreated")
        bindUI()

    }

    fun bindUI() {
        Log.d(LOG_TAG,"bindUI")

        val period = viewModel.getCurrentMinRequestInterval()

        when (period) {
            (1000*60*1) -> radioButton_1min.isChecked = true // FOR TESTING

            (1000*60*15) -> radioButton_15min.isChecked = true

            (1000*60*30) -> radioButton_30min.isChecked = true

            (1000*60*60) -> radioButton_60min.isChecked = true

            else -> {
                Toast.makeText(
                    this.context, "Error: Unpredictable MIN period state",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

}