package com.example.listaalumnos.database

import androidx.room.*
@Dao
interface AlumnoDao {
    @Query("select * from alumno order by nombre")
    fun loadAll(): List<Alumno>
    @Insert
    fun insert(alumno:Alumno)
    @Update(onConflict= OnConflictStrategy.REPLACE)
    fun update(alumno:Alumno)
    @Delete
    fun delete(alumno:Alumno)
    @Query("DELETE FROM alumno")
    fun deleteAll()
    @Query("SELECT * FROM alumno WHERE id=:id")
    fun loadById(id:Long):Alumno
}