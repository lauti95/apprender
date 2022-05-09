package com.example.firebaselogin.adaptador

import android.view.ViewGroup
import com.example.firebaselogin.base.BaseAdapter
import com.example.firebaselogin.modelo.Reporte

class AdaptadorReporte(private val listaReporte: List<Reporte>): BaseAdapter<AdaptadorReporte.ReporteHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorReporte.ReporteHolder {
        return AdaptadorReporte.ReporteHolder(parent.inflateBinding(ListaReporteBinding::inflate))
    }

    override fun onBindViewHolder(holder: AdaptadorReporte.ReporteHolder, position: Int) {
        holder.bind(listaReporte[position])
    }

    override fun getItemCount(): Int {
        return listaReporte.size
    }

    class ReporteHolder(private val binding: ListaReporteBinding):BaseViewHolder(binding) {
        fun bind(reporte: Reporte){
            binding.tvNombreReporte.text = reporte.nombre
        }
    }
}