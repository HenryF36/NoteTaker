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
                        editText.text.toString().trim().lowercase(Locale.ROOT), ""  // Trim the input
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
                val noteName = editText.text.toString().trim().lowercase(Locale.ROOT)  // Trim the input
                val noteContent = noteContentEditText.text.toString()

                // Saving logic
                val sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                try {
                    // Check if "NoteList" exists as a String and clear it if necessary
                    if (sharedPreferences.contains("NoteList") && sharedPreferences.all["NoteList"] is String) {
                        editor.remove("NoteList").apply()  // Clear the old String type entry
                    }

                    // Retrieve existing notes as a mutable set, or create a new set if null
                    val existingNotes = sharedPreferences.getStringSet("NoteList", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

                    // Add new note name to the set and save it back
                    existingNotes.add(noteName)
                    editor.putStringSet("NoteList", existingNotes)

                    // Save the note content separately
                    editor.putString(noteName, noteContent)
                    editor.apply()

                    Log.d("NoteTakerL", "Note saved successfully as $noteName.")
                    Log.d("NoteTakerL", "Updated NoteList: ${existingNotes.joinToString(", ")}")
                } catch (e: Exception) {
                    Log.e("NoteTakerL", "Error while saving note:", e)
                }

                dialog.dismiss()
            }

            dialog.show()
            Log.d("NoteTakerL", "NewNote started.")
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
                } catch (e: Exception) {
                    Log.e("NoteTakerL", "Error while deleting NoteList:", e)
                }
            }

            dialog.show() // Show the dialog
            Log.d("NoteTakerL","Dialog Shown.")

        }


        val delNoteButton: Button = findViewById(R.id.DelNoteB)
        delNoteButton.setOnClickListener {
            Log.d("NoteTakerL", "Delete Note Start")
            // TODO: Add delete note functionality
        }
        // TODO : Delete Storage Button with View for Conferm.
    }
}
