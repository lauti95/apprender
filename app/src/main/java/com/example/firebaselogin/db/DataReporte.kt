package com.example.firebaselogin.db

import com.example.firebaselogin.modelo.Reporte

class DataReporte {

    fun cargarReporte(): List<Reporte>{
        val reporte1 = Reporte("Lauti", 2, 10, 8, 7)
        val reporte2 = Reporte("Jorge", 10, 9, 6, 7)
        return listOf(Reporte("María", 2,3,5, 10), reporte1, reporte2)
    }
}