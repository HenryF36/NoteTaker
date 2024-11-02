package com.hfcode.notetaker

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.hfcode.notetaker.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)

        val openNoteButton: Button = findViewById(R.id.OpenNoteB)
        openNoteButton.setOnClickListener {
            Log.d("NoteTakerL", "Open Note Start")

        }

        val newNoteButton: Button = findViewById(R.id.NewNoteB)
        newNoteButton.setOnClickListener {
            Log.d("NoteTakerL", "New Note Start")

            // Initialize dialog
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.activity_new_note)
            dialog.setCancelable(true)

            // Customize dialog elements
            val closeButton = dialog.findViewById<Button>(R.id.canc)
            val saveButton = dialog.findViewById<Button>(R.id.SaveB)

            closeButton.setOnClickListener {
                dialog.dismiss()
                Log.d("NoteTakerL", "NewNote Finished, content discarded.")
            }

            saveButton.setOnClickListener {
                Log.d("NoteTakerL", "Save Clicked.")

                // Retrieve latest user input
                val noteName = dialog.findViewById<EditText>(R.id.NnoteName).text.toString()
                val noteContent = dialog.findViewById<EditText>(R.id.NnoteCON).text.toString()

                // Saving logic
                val sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                try {
                    editor.putString("NoteList", "${sharedPreferences.getString("NoteList", " ")} ${noteName},")
                    editor.putString(noteName, noteContent)  // Save Note Content
                    editor.apply()  // Commit changes only once
                    Log.d("NoteTakerL", "Note saved successfully as $noteName.")
                } catch (e: Exception) {
                    Log.e("NoteTakerL", "Error while saving note:", e)
                }

                dialog.dismiss()
            }

            dialog.show()
            Log.d("NoteTakerL", "NewNote started.")
        }


        val viewAllNotesButton: Button = findViewById(R.id.ViewAllN)
        viewAllNotesButton.setOnClickListener {
            Log.d("NoteTakerL", "View All Notes Start")
        }

        val delNoteButton: Button = findViewById(R.id.DelNoteB)
        delNoteButton.setOnClickListener {
            Log.d("NoteTakerL", "Delete Note Start")
        }
    }
}
