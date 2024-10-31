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

            // Set dialog properties
            dialog.setCancelable(true)

            // Customize dialog elements
            //val closeButton = dialog.findViewById<Button>(R.id.CloseB)
            //val NoteNameIm = dialog.findViewById<EditText>(R.id.EnterPassword)

            //closeButton.setOnClickListener {
                // Close Button

            //}

            // Show the dialog
            dialog.show()
            Log.d("NoteTakerL","NewNoteAct started.")
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
