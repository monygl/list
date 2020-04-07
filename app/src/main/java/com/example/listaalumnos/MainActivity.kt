package com.example.listaalumnos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listaalumnos.database.Alumno
import com.example.listaalumnos.database.AppDatabase
import com.example.listaalumnos.helper.doAsync

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

   private lateinit var viewAdapter: AlumnoAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    val ListaAlumnos: List<Alumno> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

  viewManager=LinearLayoutManager(this)
  viewAdapter= AlumnoAdapter(ListaAlumnos,this,{ alumno:Alumno->onItemClickListener(alumno)})

 recyclerView.apply{
     setHasFixedSize(true)
     layoutManager=viewManager
     adapter=viewAdapter
     addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
 }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {


                doAsync{
                    val position = viewHolder.adapterPosition
                    val alumnos = viewAdapter.getAlumno()
                    AppDatabase.getInstance(this@MainActivity)?.alumnoDao()?.delete(alumnos[position])
                    retrieve()
                }.execute()


            }
        }).attachToRecyclerView(recyclerView)

        fab.setOnClickListener { view ->

     val addIntent = Intent(this@MainActivity, AddAlumnoActivity::class.java)
     startActivity(addIntent)
 }
}

override fun onCreateOptionsMenu(menu: Menu): Boolean {

 menuInflater.inflate(R.menu.menu_main, menu)
 return true
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {

 return when (item.itemId) {
     R.id.action_settings -> true
     else -> super.onOptionsItemSelected(item)
 }
}
    private fun onItemClickListener(alumno:Alumno){
        val intent=Intent(this,AddAlumnoActivity::class.java)
        intent.putExtra(AddAlumnoActivity.EXTRA_ID,alumno.id)
        startActivity(intent)

    }

    override fun onResume(){
        super.onResume()
        retrieve()
    }

    private fun retrieve(){
        doAsync{
            val alumnos=AppDatabase.getInstance(this@MainActivity)?.alumnoDao()?.loadAll()
            runOnUiThread{
                viewAdapter.setAlumno(alumnos!!)
            }

        }.execute()
    }
}
