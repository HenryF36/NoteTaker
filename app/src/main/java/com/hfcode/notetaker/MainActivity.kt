package com.hfcode.notetaker

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)

        val newNoteButton: Button = findViewById(R.id.NewNoteB)
        newNoteButton.setOnClickListener {
            Log.d("NoteTakerL", "New Note Start")
            Onote(this)
        }


        val viewAllNoteNButton: Button = findViewById(R.id.ViewAllN)
        viewAllNoteNButton.setOnClickListener {
            Log.d("NoteTakerL", "View all Note Names Start")
            // Initialize dialog
            ViewAnote(this)
        }


        val delNoteButton: Button = findViewById(R.id.DelNoteB)
        delNoteButton.setOnClickListener {
            Log.d("NoteTakerL", "Delete Note Start")
            Dnote(this)
        }

        // TODO : Delete Storage Button with View for Conferm.
    }


}
