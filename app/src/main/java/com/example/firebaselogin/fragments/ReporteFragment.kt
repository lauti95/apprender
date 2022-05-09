package com.example.firebaselogin.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaselogin.adaptador.AdaptadorCurso
import com.example.firebaselogin.adaptador.AdaptadorReporte
import com.example.firebaselogin.base.BaseFragment
import com.example.firebaselogin.databinding.ReporteFragmentBinding
import com.example.firebaselogin.db.DataCurso
import com.example.firebaselogin.db.DataReporte

class ReporteFragment : BaseFragment<ReporteFragmentBinding>(ReporteFragmentBinding::inflate) {

    private var dbReporte = DataReporte().cargarReporte()
    private var columnas : Int = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        with(binding.rvListasReporte){
            layoutManager = when{
                columnas == 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnas)
            }
            adapter = AdaptadorReporte(dbReporte)
        }
    }


}