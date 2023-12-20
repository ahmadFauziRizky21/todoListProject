package com.example.todolistproject


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val context: android.content.Context, private var todoList: List<TodoList> ) :
    RecyclerView.Adapter<MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun searchDataList(searchList: List<TodoList>) {
        todoList = searchList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.recName.text = todoList[position].name
        holder.recDesc.text= todoList[position].desc
        val isChecked = todoList[position].isChecked
        val imageResource = if (isChecked) R.drawable.checked_24 else R.drawable.unchecked_24
        holder.recIsChecked.setImageResource(imageResource)
    }

}

class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var recName: TextView
    var recDesc: TextView
    var recCard: CardView
    var recIsChecked:ImageButton

    init {
        recName = itemView.findViewById(R.id.tv_item_title)
        recDesc = itemView.findViewById(R.id.tv_item_description)
        recCard = itemView.findViewById(R.id.taskCellContainer)
        recIsChecked = itemView.findViewById(R.id.completeButton)
    }


}
