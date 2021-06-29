package com.example.practicingkotlin.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicingkotlin.NotesAdapter
import com.example.practicingkotlin.R
import com.example.practicingkotlin.realm.Notes
import io.realm.Realm
import io.realm.RealmResults

class AddNotesActivity : AppCompatActivity(),onDeleteListener {

    private lateinit var titleNote: EditText
    private lateinit var descriptionNote: EditText
    private lateinit var saveNotesButton: Button
    private lateinit var deleteNotesButton: Button
    private lateinit var realm: Realm

    private lateinit var notesList: ArrayList<Notes>
    private lateinit var notesRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        //initViews
        realm = Realm.getDefaultInstance()
        titleNote = findViewById(R.id.titleTask)
        descriptionNote = findViewById(R.id.descriptionTask)
        saveNotesButton = findViewById(R.id.saveButton)
        deleteNotesButton = findViewById(R.id.deleteButton)
        notesRV = findViewById(R.id.notesRV)


        notesRV.layoutManager = LinearLayoutManager(this)
        getAllNotes()

        saveNotesButton.setOnClickListener {
            addNotesToDB()
            titleNote.setText("")
            descriptionNote.setText("")
        }
//        deleteNotesButton.setOnClickListener {
//            removeNotesFromDB()
//        }
    }

//    private fun removeNotesFromDB() {
//        realm.beginTransaction()
//        val results=realm.where(Notes::class.java).equalTo(id).findAll()
//        results.deleteAllFromRealm()
//        realm.commitTransaction()
//    }

    private fun addNotesToDB() {
        try {
            realm.beginTransaction()
            val currentIdNumber: Number? = realm.where(Notes::class.java).max("id")
            val nextID: Int
            nextID = if (currentIdNumber == null) {
                1
            } else {
                currentIdNumber.toInt() + 1
            }
            val notes = Notes()
            notes.title = titleNote.text.toString()
            notes.description = descriptionNote.text.toString()
            notes.id = nextID

            //copying&committing
            realm.copyToRealmOrUpdate(notes)
            realm.commitTransaction()

            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
            getAllNotes()
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()

        } catch (e: Exception) {
            Toast.makeText(this, "Failed $e", Toast.LENGTH_SHORT).show()

        }
    }

    private fun getAllNotes() {
        notesList = ArrayList()
        val results: RealmResults<Notes> = realm.where<Notes>(Notes::class.java).findAll()
        notesRV.adapter = NotesAdapter(results)
        notesRV.adapter!!.notifyDataSetChanged()

    }

    override fun setOnDeleteListener() {
        val results: RealmResults<Notes> = realm.where<Notes>(Notes::class.java).findAll()
        notesRV.adapter = NotesAdapter(results)
        notesRV.adapter!!.notifyDataSetChanged()
    }
}