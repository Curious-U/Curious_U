package com.example.curious_u

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DemoFragment : Fragment() {
    // private val viewModel by viewModels<DataViewModel>()
    private val viewModel: DataViewModel by activityViewModels()
    private val countViewModel: CountViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Log.d("mytag", "? " + viewModel.getData()!!)
        val view = inflater.inflate(R.layout.fragment_demo, container, false)
        view.findViewById<Button>(R.id.inc_btn).setOnClickListener {
            countViewModel.setData(100)
        }
        return view
    }

}