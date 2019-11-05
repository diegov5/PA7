/*
 * This program creates a note taking application that allows you to create new notes, edit them, and delete them
 *
 * CPSC 312-01, Fall 2019
 * Programming Assignment #6
 *
 * @author Diego Valdez and Patrick Seminatore
 * @version v1.0 10/22/19
 */

package com.example.pa6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner categorySpinner;
    EditText title;
    EditText content;
    GridLayout noteGridLayout;
    Button doneButton;

    /*
     *   This method handles creating the root grid layout, and calling the methods necessary
     *      for creating the rest of the widgets on the page
     *
     *   Parameters: savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteGridLayout = new GridLayout(NoteActivity.this);
        noteGridLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(noteGridLayout);

        noteGridLayout.setColumnCount(2);
        noteGridLayout.setRowCount(3);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            createEditText(intent.getStringExtra("noteTitle"));
            createSpinner(Objects.requireNonNull(intent.getStringExtra("noteCategory")));
            createContentEditText(intent.getStringExtra("noteContent"));
        } else {
            createEditText("");
            createSpinner("");
            createContentEditText("");
        }
        createDoneButton();
    }

    /*
     *   This method handles creating the edit text that the user uses for the title
     *
     *   Parameters: existingText: null if the note is new, pre populated with old content if note already existed
     */
    public void createEditText(String existingText){
        title = new EditText(this);

        GridLayout.Spec rowSpec = GridLayout.spec(0, 1);
        // row start index, row span, row weight
        GridLayout.Spec colSpec = GridLayout.spec(0,1 , 1);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, colSpec);
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        title.setLayoutParams(layoutParams);
        title.setHint("Title");
        title.setText(existingText);

        noteGridLayout.addView(title);
    }

    /*
     *   This method handles creating the edit text that the user uses for the note category
     *
     *   Parameters: existingChoice: null if the note is new, pre populated with old choice if note already existed
     */
    public void createSpinner(String existingChoice){
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        if(!existingChoice.isEmpty()) {
            if (existingChoice.equals("Personal"))
                categorySpinner.setSelection(0);
            if (existingChoice.equals("School"))
                categorySpinner.setSelection(1);
            if (existingChoice.equals("Work"))
                categorySpinner.setSelection(2);
            if (existingChoice.equals("Other"))
                categorySpinner.setSelection(3);
        }
        noteGridLayout.addView(categorySpinner);
    }

    /*
     *   This method handles creating the edit text that the user uses for the note content
     *
     *   Parameters: existingText: null if the note is new, pre populated with old content if note already existed
     */
    public void createContentEditText(String existingText){
        content = new EditText(this);

        GridLayout.Spec rowSpec = GridLayout.spec(1, 1, 1);
        // row start index, row span, row weight
        GridLayout.Spec colSpec = GridLayout.spec(0,2 , 1);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, colSpec);
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        content.setLayoutParams(layoutParams);
        content.setHint("Content");
        content.setText(existingText);
        content.setGravity(Gravity.CLIP_VERTICAL);

        noteGridLayout.addView(content);

    }

    /*
     *   This method handles creating the button that will save the newly created or edited note
     *
     */
    public void createDoneButton(){
        doneButton = new Button(this);

        doneButton.setText(R.string.doneButtonText);

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

    /*
     *   This method handles the functionality for saving a note
     *
     *   Parameters: view
     */
    @Override
    public void onClick(View view) {
        String finalTitle = title.getText().toString();
        if (!finalTitle.isEmpty()) {
            String finalContent = content.getText().toString();
            String finalCategory = categorySpinner.getItemAtPosition(categorySpinner.getSelectedItemPosition()).toString();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("noteTitle", finalTitle);
            intent.putExtra("noteContent", finalContent);
            intent.putExtra("noteCategory", finalCategory);
            setResult(1, intent);
            this.finish();
        }
        else{
            Toast noTitle = Toast.makeText(this, "Invalid note, must have title!", Toast.LENGTH_SHORT);
            noTitle.show();
        }
    }
}
