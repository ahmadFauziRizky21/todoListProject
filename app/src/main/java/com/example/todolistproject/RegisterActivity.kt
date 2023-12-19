package com.example.todolistproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todolistproject.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var rootNode: FirebaseDatabase
    private lateinit var reference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginJump.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent);
        }
        binding.btnRegister.setOnClickListener{
            val userName = binding.userName.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val name = binding.fullName.text.toString().trim()
            val pass = binding.password.text.toString().trim()
            val confirmPass = binding.confirmPass.text.toString().trim()
            rootNode = FirebaseDatabase.getInstance()
            reference = rootNode.getReference("users")
            if (name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if (pass == confirmPass){
                    val userReg = userRegister(userName, name, email, pass)
                    reference.child(userName).setValue(userReg)
                    Toast.makeText(this, "Account Successfully Created", Toast.LENGTH_SHORT).show()
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                        if (it.isSuccessful) {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    Toast.makeText(this, "Password is not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Fields have to be filled", Toast.LENGTH_SHORT).show()
            }
        }

    }
}




