package com.hfcode.notetaker

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)

        val newNoteButton: Button = findViewById(R.id.NewNoteB)
        newNoteButton.setOnClickListener {
            Log.d("NoteTakerL", "New Note Start")
            val onote = Onote()
            onote.OpenNote(this)
        }


        val viewAllNoteNButton: Button = findViewById(R.id.ViewAllN)
        viewAllNoteNButton.setOnClickListener {
            Log.d("NoteTakerL", "View all Note Names Start")

            // Initialize dialog
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.activity_view_all_note_names)
            dialog.setCancelable(true)

            // Customize dialog elements
            val closeButton = dialog.findViewById<Button>(R.id.VANcanc)
            val noteNbox = dialog.findViewById<TextView>(R.id.ListB)  // Make sure this is a TextView in your layout

            closeButton.setOnClickListener {
                dialog.dismiss()
                Log.d("NoteTakerL", "VAN Finished.")
            }

            dialog.findViewById<Button>(R.id.DelNoteN).setOnClickListener {
                val sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                try {
                    // Remove the NoteList entry
                    editor.remove("NoteList")
                    editor.apply()  // Apply changes to SharedPreferences

                    Log.d("NoteTakerL", "NoteList deleted successfully.")
                    noteNbox.text = ""  // Clear the TextView after deletion
                } catch (e: Exception) {
                    Log.e("NoteTakerL", "Error while deleting NoteList:", e)
                }
            }

            // Retrieve existing notes and display them in NoteNbox
            val sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE)
            val existingNotes = sharedPreferences.getStringSet("NoteList", emptySet()) ?: emptySet()
            val notesList = existingNotes.joinToString(", ") // Convert the set to a comma-separated string

            noteNbox.text = notesList  // Set the text to the TextView

            dialog.show() // Show the dialog
            Log.d("NoteTakerL", "Dialog Shown.")
        }


        val delNoteButton: Button = findViewById(R.id.DelNoteB)
        delNoteButton.setOnClickListener {
            Log.d("NoteTakerL", "Delete Note Start")

            // Initialize dialog
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.activity_del_note)
            dialog.setCancelable(true)

            val closeButton = dialog.findViewById<Button>(R.id.DNcanc)
            val editText = dialog.findViewById<EditText>(R.id.DnoteName)

            closeButton.setOnClickListener {
                dialog.dismiss()
                Log.d("NoteTakerL", "DN Finished. Note not Deleted")
            }

            dialog.findViewById<Button>(R.id.DeleteButton).setOnClickListener {
                val noteName = editText.text.toString().trim().lowercase()

                // Check if the note name is empty
                if (noteName.isNotEmpty()) {
                    deleteNote(noteName)
                    editText.text.clear() // Clear the input field after deletion
                } else {
                    Toast.makeText(this, "Please enter a note name.", Toast.LENGTH_SHORT).show()
                }
            }

            dialog.show() // Show the dialog
            Log.d("NoteTakerL", "Dialog Shown.")
        }

        // TODO : Delete Storage Button with View for Conferm.
    }

    fun deleteNote(noteName: String) {
        val sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Retrieve existing notes
        val existingNotes = sharedPreferences.getStringSet("NoteList", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        // Check if the note exists
        if (existingNotes.contains(noteName)) {
            // Remove the note from the set
            existingNotes.remove(noteName)

            // Update the NoteList in SharedPreferences
            editor.putStringSet("NoteList", existingNotes)

            // Remove the note content from SharedPreferences
            editor.remove(noteName)  // Remove the associated note content
            editor.apply()  // Apply changes

            Log.d("NoteTakerL", "Note '$noteName' deleted successfully.")
        } else {
            Log.e("NoteTakerL", "Note '$noteName' not found.")
        }
    }
}
