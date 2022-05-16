package com.example.firebaselogin.adaptador

import android.view.ViewGroup
import com.example.firebaselogin.base.BaseAdapter
import com.example.firebaselogin.modelo.Alumno
import com.example.firebaselogin.databinding.ListaAlumnosBinding
import com.squareup.picasso.Picasso

class AdaptadorAlumno(private val listaCurso: List<Alumno>) :
        BaseAdapter<AdaptadorAlumno.AlumnoHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoHolder {
            return AlumnoHolder(parent.inflateBinding(ListaAlumnosBinding::inflate))
        }

        override fun onBindViewHolder(holder: AlumnoHolder, position: Int) {
            holder.bind(listaCurso[position])
        }

        override fun getItemCount(): Int {
            return listaCurso.size
        }

        class AlumnoHolder(private val binding: ListaAlumnosBinding) : BaseViewHolder(binding) {
            fun bind(alumno: Alumno) {
                binding.tvAlumno.text = alumno.nombre
                Picasso.get().load(alumno.imagen).into(binding.imgAlumno)
            }
        }
    }

