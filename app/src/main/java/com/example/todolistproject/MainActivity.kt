package com.example.todolistproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todolistproject.databinding.ActivityMainBinding
import com.google.firebase.database.*
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var databaseReference: DatabaseReference? = null
    private var eventListener: ValueEventListener? = null
    private lateinit var adapter: MyAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoList: ArrayList<TodoList>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val gridLayoutManager = GridLayoutManager(this@MainActivity, 1)
        binding.todoListRecyclerView.layoutManager = gridLayoutManager
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()


        todoList = ArrayList()
        adapter = MyAdapter(this@MainActivity, todoList)
        binding.todoListRecyclerView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("todo")
        dialog.show()

        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                todoList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(TodoList::class.java)
                    if (dataClass != null) {
                        todoList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })



        binding.newTaskButton.setOnClickListener {
            val intent = Intent(this@MainActivity, UploadActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    fun searchList(text: String) {
        val searchList = java.util.ArrayList<TodoList>()
        for (dataClass in todoList) {
            if (dataClass.name?.lowercase()
                    ?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                searchList.add(dataClass)
            }
        }
        adapter.searchDataList(searchList)

    }
}
