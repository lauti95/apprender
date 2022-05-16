package com.example.firebaselogin.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Alumno (
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val nombre: String = "",
    val apellido: String = "" ,
    val dni: Int = 0,
    val imagen: String = ""
)
{
    val urlImagen
        get() = "IMG_$id.jpg"
}