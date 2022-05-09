package com.example.firebaselogin.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaselogin.R
import com.example.firebaselogin.adaptador.AdaptadorCurso
import com.example.firebaselogin.base.BaseFragment
import com.example.firebaselogin.databinding.CursoFragmentBinding
import com.example.firebaselogin.db.DataCurso

class CursoFragment: BaseFragment<CursoFragmentBinding>(CursoFragmentBinding::inflate) {

    private var dbCurso = DataCurso().cargarCursos()
    private var columnas : Int = 1

    override fun onViewCreated(view: View, savedInstanceState:Bundle?){
        with(binding.rvListasCurso){
            layoutManager = when{
                columnas == 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnas)
            }
            adapter = AdaptadorCurso(dbCurso)
        }
    }


}