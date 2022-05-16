package com.example.firebaselogin.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.firebaselogin.modelo.Alumno

@Dao
interface AlumnoDao {
    @Query("SELECT * FROM Alumno")
    fun getAlumnos():LiveData<List<Alumno>>

    @Update
    fun updateAlumnos(alumno: Alumno)

    @Insert
    fun addAlumnos(alumno : Alumno)

    @Delete
    fun deleteAlumnos(alumno : Alumno)
}