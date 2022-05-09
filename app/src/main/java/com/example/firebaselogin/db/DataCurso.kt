package com.example.firebaselogin.db

import com.example.firebaselogin.modelo.Curso

class DataCurso {

    fun cargarCursos(): List<Curso>{

        val curso1 = Curso("Inglés", "Cambridge", "Ultra avanzado")
        val curso2 = Curso("Matemática", "IAE", "Principiante")
        return listOf(Curso("Kotlin", "Jorge School", "Noob"), curso1, curso2)

    }

}