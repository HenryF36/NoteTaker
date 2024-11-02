package com.hfcode.notetaker

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.core.view.isVisible
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)

        val newNoteButton: Button = findViewById(R.id.NewNoteB)
        newNoteButton.setOnClickListener {
            Log.d("NoteTakerL", "New Note Start")

            // Initialize dialog
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.activity_open_note)
            dialog.setCancelable(true)

            // Customize dialog elements
            val closeButton = dialog.findViewById<Button>(R.id.canc)
            val saveButton = dialog.findViewById<Button>(R.id.SaveB)
            val editText = dialog.findViewById<EditText>(R.id.NnoteName)
            val noteContentEditText = dialog.findViewById<EditText>(R.id.NnoteCON)

            editText.setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
                ) {
                    Log.d("NoteTakerL", "Text submitted: ${editText.text}")

                    val sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE)
                    val savedContent = sharedPreferences.getString(
                        editText.text.toString().lowercase(Locale.ROOT), ""
                    ) ?: ""
                    noteContentEditText.setText(savedContent)  // Set text to EditText
                    noteContentEditText.isVisible = true
                    saveButton.isVisible = true
                    true  // Indicate the event was handled
                } else {
                    false
                }
            }

            closeButton.setOnClickListener {
                dialog.dismiss()
                Log.d("NoteTakerL", "NewNote Finished, content discarded.")
            }

            saveButton.setOnClickListener {
                Log.d("NoteTakerL", "Save Clicked.")

                // Retrieve latest user input
                val noteName = editText.text.toString().lowercase(Locale.ROOT)
                val noteContent = noteContentEditText.text.toString()

                // Saving logic
                val sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                try {
                    val existingNotes = sharedPreferences.getString("NoteList", " ") ?: " "
                    editor.putString("NoteList", "$existingNotes$noteName,")
                    editor.putString(noteName, noteContent)
                    editor.apply()
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
            // TODO: Add logic to view all notes
        }

        val delNoteButton: Button = findViewById(R.id.DelNoteB)
        delNoteButton.setOnClickListener {
            Log.d("NoteTakerL", "Delete Note Start")
            // TODO: Add delete note functionality
        }
    }
}
