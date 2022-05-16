package com.example.firebaselogin.db

import com.example.firebaselogin.modelo.Alumno
import java.util.*

class DataAlumnos {

    fun cargarAlumnos(): List<Alumno>{

        val alumno1 = Alumno(UUID.randomUUID(),"Lauti", "Negri",39391091, "https://doblaje.fandom.com/es/wiki/Clifford_(personaje)?file=Clifford.png")
        val alumno2 = Alumno(UUID.randomUUID(),"Jorge", "Reyes", 39391071,"https://spongebob.fandom.com/es/wiki/Bob_Esponja_Pantalones_Cuadrados?file=Spongebob.png" )
        return listOf(alumno1, alumno2)

    }

}