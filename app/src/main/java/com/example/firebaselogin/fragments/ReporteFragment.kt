package com.example.firebaselogin.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebaselogin.R

class ReporteFragment : Fragment() {

    companion object {
        fun newInstance() = ReporteFragment()
    }

    private lateinit var viewModel: ReporteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reporte_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReporteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}