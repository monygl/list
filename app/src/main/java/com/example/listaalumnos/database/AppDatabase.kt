package com.example.listaalumnos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.listaalumnos.helper.DateConverter

@Database(entities =arrayOf(Alumno::class),version=1,exportSchema=false )
@TypeConverters(DateConverter::class)
abstract class AppDatabase: RoomDatabase() {
    companion object{
        private var Instance:AppDatabase?=null
        fun  getInstance(context: Context):AppDatabase?{
            if (Instance==null){
                synchronized(AppDatabase::class){
                    Instance= Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "registro4.db"
                    ).build()
                }
            }
            return Instance
        }
    }
    abstract fun alumnoDao():AlumnoDao

}