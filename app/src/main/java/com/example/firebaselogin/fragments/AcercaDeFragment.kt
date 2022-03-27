package com.example.firebaselogin.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebaselogin.R

class AcercaDeFragment : Fragment() {

    companion object {
        fun newInstance() = AcercaDeFragment()
    }

    private lateinit var viewModel: AcercaDeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.acerca_de_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AcercaDeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}