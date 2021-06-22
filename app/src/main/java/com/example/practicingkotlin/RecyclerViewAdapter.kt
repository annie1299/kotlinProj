package com.example.practicingkotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.practicingkotlin.database.TaskEntry
import com.example.practicingkotlin.databinding.ListItemBinding


class RecyclerViewAdapter(private val clickListener: (TaskEntry) -> Unit) :
    RecyclerView.Adapter<MyViewHolder>() {
    private val tasksList = ArrayList<TaskEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(tasksList[position], clickListener)
    }

    fun setList(tasks: List<TaskEntry>) {
        tasksList.clear()
        tasksList.addAll(tasks)
    }

}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(taskEntry: TaskEntry, clickListener: (TaskEntry) -> Unit) {
        binding.taskNameTextView.text = taskEntry.title
        binding.taskDescTextView.text = taskEntry.description
        binding.listItemLayout.setOnClickListener {
            clickListener(taskEntry)
        }
    }
}
