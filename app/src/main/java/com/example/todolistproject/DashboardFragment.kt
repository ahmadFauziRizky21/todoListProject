package com.example.todolistproject
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todolistproject.databinding.FragmentDashboardBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var dataList: ArrayList<TodoList>
    private lateinit var adapter: MyAdapter
    private lateinit var databaseReference: DatabaseReference
    private var eventListener: ValueEventListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        binding.todoListRecyclerView.layoutManager = gridLayoutManager

        val progressLayout = LayoutInflater.from(requireContext())
            .inflate(R.layout.progress_layout, binding.root as ViewGroup, false)

        val builder = AlertDialog.Builder(requireContext())
            .setView(progressLayout)

        val progressDialog: AlertDialog = builder.create()
        progressDialog.setCancelable(false)
        progressDialog.show()


        dataList = ArrayList()
        adapter = MyAdapter(requireContext(), dataList)
        binding.todoListRecyclerView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("Todo List")
        progressDialog.show()

        eventListener = databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(TodoList::class.java)
                    if (dataClass != null) {
                        dataList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
                progressDialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                progressDialog.dismiss()
            }
        })

        binding.newTaskButton.setOnClickListener {
            val intent = Intent(requireContext(), UploadActivity::class.java)
            startActivity(intent)
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        eventListener?.let { databaseReference.removeEventListener(it) }
    }
}
