package com.example.todolistproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todolistproject.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private lateinit var auth: FirebaseAuth


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val firebaseUser = auth.currentUser

        if (firebaseUser != null){
            binding.email.text = firebaseUser.email
        }
        binding.btnOut.setOnClickListener{
            logoutFunc()
        }


    }
    private fun logoutFunc() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}