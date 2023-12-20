package com.example.todolistproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.todolistproject.databinding.ActivityUploadBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat
import java.util.Calendar

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val user = auth.currentUser
    private val userId = user?.uid
    val todoRef = FirebaseDatabase.getInstance().getReference("todo").child(userId?: "")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener{
            saveData()
        }

    }

    private fun saveData() {

        val builder:AlertDialog.Builder = AlertDialog.Builder(this@UploadActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()
        uploadData()
        dialog.dismiss()

    }

    private fun uploadData() {
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()
        val isChecked : Boolean = false

        val todoClass = TodoList(name, desc, isChecked)
        val currDate = DateFormat.getDateInstance().format(Calendar.getInstance().time)

        val todoId = todoRef.push().key
        todoRef.child(todoId ?: "").setValue(todoClass).addOnCompleteListener() {
            task ->
            if (task.isSuccessful){
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{e ->
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }



    }
}