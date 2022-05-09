package com.example.firebaselogin.adaptador

import android.view.ViewGroup
import com.example.firebaselogin.base.BaseAdapter
import com.example.firebaselogin.databinding.ListaCursoBinding
import com.example.firebaselogin.modelo.Curso

class AdaptadorCurso(private val listaCurso: List<Curso>): BaseAdapter<AdaptadorCurso.CursoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoHolder {
        return CursoHolder(parent.inflateBinding(ListaCursoBinding::inflate))
    }

    override fun onBindViewHolder(holder: CursoHolder, position: Int) {
        holder.bind(listaCurso[position])
    }

    override fun getItemCount(): Int {
        return listaCurso.size
    }

    class CursoHolder(private val binding: ListaCursoBinding):BaseViewHolder(binding) {
        fun bind(curso: Curso){
            binding.tvNombreCurso.text = curso.nombre
            binding.tvEscuela.text = curso.escuela
            //binding.tvGrado.text = curso.grado
        }
    }
}