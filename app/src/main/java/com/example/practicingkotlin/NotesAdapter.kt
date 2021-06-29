package com.example.practicingkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practicingkotlin.realm.Notes
import io.realm.Realm
import io.realm.RealmResults


class NotesAdapter(todos: RealmResults<Notes>) :
    RecyclerView.Adapter<NotesAdapter.TodoViewHolder>() {
    lateinit var items: RealmResults<Notes>

    init {
        this.items = todos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val layoutInflater = LayoutInflater.from(parent!!.context)
        return TodoViewHolder(layoutInflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder?.bind(this.items[position]!!)
    }

    class TodoViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val title = itemView?.findViewById<TextView>(R.id.titleTask)
        val desc = itemView?.findViewById<TextView>(R.id.descTask)
        val deleteButton = itemView?.findViewById<TextView>(R.id.deleteButton)
        val context: Context = itemView!!.context


        public fun bind(notes: Notes) {
            title?.text = notes.title
            desc?.text = notes.description

            this.deleteButton?.setOnClickListener {
                val realm = Realm.getDefaultInstance()
                realm.beginTransaction()
                val results = realm.where(Notes::class.java).equalTo("id", notes.id).findAll()
                results.deleteAllFromRealm()
                realm.commitTransaction()
            }
        }
    }
}

