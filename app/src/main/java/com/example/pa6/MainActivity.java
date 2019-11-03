package com.example.pa6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button newNoteButton;
    GridLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.mainGridLayout);
        layout.setColumnCount(1);
        layout.setRowCount(1);

        newNoteButton = new Button(this);
        newNoteButton.setText("Add New Note");

        GridLayout.Spec rowSpec = GridLayout.spec(0, 1, 1);
        // row start index, row span, row weight
        GridLayout.Spec colSpec = GridLayout.spec(0, 1, 1);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, colSpec);
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        newNoteButton.setLayoutParams(layoutParams);
        newNoteButton.setOnClickListener(this);
        layout.addView(newNoteButton);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }
}
