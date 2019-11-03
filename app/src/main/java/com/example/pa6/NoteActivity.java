package com.example.pa6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner categorySpinner;
    EditText title;
    EditText content;
    GridLayout noteGridLayout;
    Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteGridLayout = findViewById(R.id.noteGridLayout);
        noteGridLayout.setColumnCount(2);
        noteGridLayout.setRowCount(3);

        createEditText();
        createSpinner();
        createContentEditText();
        createDoneButton();
    }

    public void createEditText(){
        title = new EditText(this);

        GridLayout.Spec rowSpec = GridLayout.spec(0, 1);
        // row start index, row span, row weight
        GridLayout.Spec colSpec = GridLayout.spec(0,1 , 1);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, colSpec);
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        title.setLayoutParams(layoutParams);
        title.setHint("Title");

        noteGridLayout.addView(title);
    }

    public void createSpinner(){
        categorySpinner = new Spinner(this);

        GridLayout.Spec rowSpec = GridLayout.spec(0, 1);
        // row start index, row span, row weight
        GridLayout.Spec colSpec = GridLayout.spec(1,1 , 1);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, colSpec);
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        categorySpinner.setLayoutParams(layoutParams);
        String[] arraySpinner = new String[] {
                "Personal", "School", "Work", "Other"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        noteGridLayout.addView(categorySpinner);
    }

    public void createContentEditText(){
        content = new EditText(this);

        GridLayout.Spec rowSpec = GridLayout.spec(1, 1, 1);
        // row start index, row span, row weight
        GridLayout.Spec colSpec = GridLayout.spec(0,2 , 1);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, colSpec);
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        content.setLayoutParams(layoutParams);
        content.setHint("Content");
        content.setGravity(Gravity.CLIP_VERTICAL);

        noteGridLayout.addView(content);

    }

    public void createDoneButton(){
        doneButton = new Button(this);

        doneButton.setText("Done");

        GridLayout.Spec rowSpec = GridLayout.spec(2, 1);
        // row start index, row span, row weight
        GridLayout.Spec colSpec = GridLayout.spec(0, 2, 1);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, colSpec);
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        doneButton.setLayoutParams(layoutParams);
        doneButton.setOnClickListener(this);
        noteGridLayout.addView(doneButton);
    }

    @Override
    public void onClick(View view) {
        this.finish();
    }
}
