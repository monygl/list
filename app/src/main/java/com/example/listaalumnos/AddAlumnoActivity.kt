package com.example.listaalumnos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.listaalumnos.database.Alumno
import com.example.listaalumnos.database.AppDatabase
import com.example.listaalumnos.helper.doAsync
import kotlinx.android.synthetic.main.activity_add_alumno.*
import java.util.*

class AddAlumnoActivity : AppCompatActivity() {

    companion object {
        val EXTRA_ID="extraId"
        val INSTANCE_ID="instanceId"
        private val DEFAULT_ID=-1
        private val TAG= AddAlumnoActivity::class.java.simpleName

    }

    private var mTaskId= AddAlumnoActivity.DEFAULT_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alumno)

        saveButton.setOnClickListener{
            onSaveButtonClicked()
        }

        if (savedInstanceState !=null && savedInstanceState.containsKey(AddAlumnoActivity.INSTANCE_ID)){
            mTaskId=savedInstanceState.getInt(
                AddAlumnoActivity.INSTANCE_ID,
                AddAlumnoActivity.DEFAULT_ID
            )
        }
        val intent=intent
        if(intent !=null && intent.hasExtra(AddAlumnoActivity.EXTRA_ID)){
            saveButton.text=getString(R.string.update_button).toString()
            if (mTaskId === AddAlumnoActivity.DEFAULT_ID){
                mTaskId=intent.getLongExtra(AddAlumnoActivity.EXTRA_ID, AddAlumnoActivity.DEFAULT_ID.toLong()).toInt()
                doAsync{
                    val alumno= AppDatabase.getInstance(this@AddAlumnoActivity)?.alumnoDao()?.loadById(mTaskId.toLong())
                    runOnUiThread{
                        populateUI(alumno!!)
                    }

                }.execute()
            }
        }
    }
    override fun onSaveInstanceState(outState:Bundle?, outPersistentState: PersistableBundle?){
        outState?.putInt(AddAlumnoActivity.INSTANCE_ID, mTaskId)
        super.onSaveInstanceState(outState, outPersistentState)
    }
    private fun populateUI(alumno: Alumno){
        if (alumno==null) return
        editTextNombre.setText(alumno.nombre)
        tvApellido.setText(alumno.apellido)
        tvEspe.setText(alumno.especialidad)
        tvDom.setText(alumno.domicilio)
        tvTel.setText(alumno.telefono)
    }

    fun onSaveButtonClicked(){
        val nombre=editTextNombre.text.toString()
        val apellido=tvApellido.text.toString()
        val especialidad=tvEspe.text.toString()
        val domicilio=tvDom.text.toString()
        val telefono=tvTel.text.toString()


        val Entry= Alumno(nombre=nombre, apellido=apellido,domicilio=domicilio,telefono=telefono,especialidad=especialidad,updatedAt = Date())
        doAsync{
            if (mTaskId == AddAlumnoActivity.DEFAULT_ID){
                AppDatabase.getInstance(this)!!.alumnoDao().insert(Entry)
            }else{
                Entry.id=mTaskId.toLong()
                AppDatabase.getInstance(this)!!.alumnoDao().update(Entry)
            }
            finish()
        }.execute()
    }

}

