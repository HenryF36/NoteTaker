package com.hfcode.notetaker

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// Change 'context' to 'Context' for proper type usage
fun Dnote(context: Context) {

    // Initialize dialog
    val dialog = Dialog(context)
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
            deletenote(noteName, context)  // Pass context to deletenote function
            editText.text.clear() // Clear the input field after deletion
        } else {
            Toast.makeText(context, "Please enter a note name.", Toast.LENGTH_SHORT).show()
        }
    }

    dialog.show() // Show the dialog
    Log.d("NoteTakerL", "Dialog Shown.")
}

// Modify the deletenote function to accept context as a parameter
fun deletenote(noteName: String, context: Context) {
    // Use the passed context to get SharedPreferences
    val sharedPreferences = context.getSharedPreferences("Notes", Context.MODE_PRIVATE)
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
