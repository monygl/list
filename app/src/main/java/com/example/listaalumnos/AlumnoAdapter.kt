package com.example.listaalumnos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listaalumnos.database.Alumno
import kotlinx.android.synthetic.main.alumno_layout.view.*
import java.text.SimpleDateFormat
import java.util.*


class AlumnoAdapter(private var mTaskEntries:List<Alumno>, private val mContext: Context, private val clickListener: (Alumno) -> Unit) : RecyclerView.Adapter<AlumnoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoAdapter.ViewHolder {

        val layoutInflater = LayoutInflater.from(mContext)
        return AlumnoAdapter.ViewHolder(layoutInflater.inflate(R.layout.alumno_layout, parent, false)
        )

    }

    override fun getItemCount(): Int = mTaskEntries.size

    override fun onBindViewHolder(holder: AlumnoAdapter.ViewHolder, position: Int) {
        holder.bind(mTaskEntries[position], mContext, clickListener)
    }

    fun setAlumno(taskEntries: List<Alumno>){
        mTaskEntries = taskEntries
        notifyDataSetChanged()
    }

    fun getAlumno():List<Alumno> = mTaskEntries

    class ViewHolder (itemView: View) :RecyclerView.ViewHolder(itemView) {

        fun bind (alumno: Alumno, context: Context, clickListener: (Alumno) -> Unit){

            itemView.tvNombre.text = alumno.nombre
            itemView.tvApe.text = alumno.apellido
            itemView.tvdomi.text = alumno.domicilio
            itemView.tvtele.text = alumno.telefono
            itemView.tvEsp.text = alumno.especialidad

            itemView.UpdatedAt.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(alumno.updatedAt).toString()
            itemView.setOnClickListener{ clickListener(alumno)}
        }
    }
}