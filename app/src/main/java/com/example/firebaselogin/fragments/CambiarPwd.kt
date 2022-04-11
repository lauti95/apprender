package com.example.firebaselogin.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebaselogin.R

class CambiarPwd : Fragment() {

    companion object {
        fun newInstance() = CambiarPwd()
    }

    private lateinit var viewModel: CambiarPwdViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cambiar_pwd_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CambiarPwdViewModel::class.java)
        // TODO: Use the ViewModel
    }

}