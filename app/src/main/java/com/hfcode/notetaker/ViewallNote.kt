package com.hfcode.notetaker

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.TextView

fun ViewAnote(context: Context) {
    val dialog = Dialog(context)
    dialog.setContentView(R.layout.activity_view_all_note_names)
    dialog.setCancelable(true)

    // Customize dialog elements
    val closeButton = dialog.findViewById<Button>(R.id.VANcanc)
    val noteNbox = dialog.findViewById<TextView>(R.id.ListB)  // Ensure this is a TextView in your layout

    closeButton.setOnClickListener {
        dialog.dismiss()
        Log.d("NoteTakerL", "VAN Finished.")
    }

    dialog.findViewById<Button>(R.id.DelNoteN).setOnClickListener {
        // Use the context parameter to get SharedPreferences
        val sharedPreferences = context.getSharedPreferences("Notes", Context.MODE_PRIVATE)
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

    // Retrieve existing notes and display them in noteNbox
    val sharedPreferences = context.getSharedPreferences("Notes", Context.MODE_PRIVATE)
    val existingNotes = sharedPreferences.getStringSet("NoteList", emptySet()) ?: emptySet()
    val notesList = existingNotes.joinToString(", ") // Convert the set to a comma-separated string

    noteNbox.text = notesList  // Set the text to the TextView

    dialog.show() // Show the dialog
    Log.d("NoteTakerL", "Dialog Shown.")
}
