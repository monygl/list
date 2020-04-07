package com.example.listaalumnos.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName="alumno")
data class Alumno (
    @PrimaryKey(autoGenerate=true)
    var id:Long=0,
    var nombre:String="",
    var apellido:String="",
    var domicilio:String="",
    var telefono:String="",
    var especialidad:String="",
    @ColumnInfo(name="updated_at")
    var updatedAt: Date
)