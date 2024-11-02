package com.hfcode.notetaker

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.core.view.isVisible
import java.util.Locale

public class Onote {

    // Method that accepts a context parameter
     fun OpenNote(context: Context) {
        // Initialize dialog
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.activity_open_note)
        dialog.setCancelable(true)

        // Customize dialog elements
        val closeButton = dialog.findViewById<Button>(R.id.DNcanc)
        val saveButton = dialog.findViewById<Button>(R.id.SaveDN)
        val editText = dialog.findViewById<EditText>(R.id.DnoteName)
        val noteContentEditText = dialog.findViewById<EditText>(R.id.NnoteCON)

        editText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            ) {
                Log.d("NoteTakerL", "Text submitted: ${editText.text}")

                val sharedPreferences = context.getSharedPreferences("Notes", Context.MODE_PRIVATE)
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
            val sharedPreferences = context.getSharedPreferences("Notes", Context.MODE_PRIVATE)
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
}
