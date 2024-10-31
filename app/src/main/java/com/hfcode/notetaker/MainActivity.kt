package com.hfcode.notetaker

import android.os.Bundle
import android.util.Log
import android.widget.Button
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
