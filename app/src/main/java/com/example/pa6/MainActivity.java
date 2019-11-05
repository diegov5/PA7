package com.example.pa6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button newNoteButton;
    GridLayout layout;
    ListView listOfNotes;
    List<Note> myNotes = new ArrayList<>();
    ArrayAdapter<Note> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new GridLayout(MainActivity.this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setContentView(layout);
        layout.setColumnCount(1);
        layout.setRowCount(2);

        createNewNoteButton();

        Intent intent = getIntent();
        if (intent.getStringExtra("noteTitle") != null){
            Note newNote = new Note(intent.getStringExtra("noteTitle"), intent.getStringExtra("noteContent"), intent.getStringExtra("noteCategory"));
            createListView(newNote);
        } else
            createListView(null);
    }

    public void createNewNoteButton(){
        newNoteButton = new Button(this);
        newNoteButton.setText(R.string.newNoteText);

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

    public void createListView(Note newNote){
        listOfNotes = new ListView(this);
        GridLayout.Spec rowSpec = GridLayout.spec(1, 1, 1);
        // row start index, row span, row weight
        GridLayout.Spec colSpec = GridLayout.spec(0, 1, 1);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, colSpec);
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        listOfNotes.setLayoutParams(layoutParams);

        arrayAdapter  = new ArrayAdapter<>(
                this, // reference to the current activity
                android.R.layout.simple_list_item_1, // layout for each row in the list view (item in the data source)
                myNotes // data source
        );

        listOfNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
                final String selection = adapterView.getItemAtPosition(i).toString();
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                final int position = i;
                alertBuilder.setTitle("Delete Note")
                        .setMessage("Are you sure you want to delete your " + selection + " note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                myNotes.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .setNeutralButton("Share Note", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Note selectedNote = (Note) adapterView.getItemAtPosition(position);
                                String shareBody = selectedNote.getContent();
                                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, selectedNote.getTitle());
                                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share content of note using"));
                            }
                        });
                alertBuilder.show();
                return true; // false if the event was not consumed, true otherwise
            }
        });

        if (newNote != null)
            myNotes.add(newNote);


        listOfNotes.setAdapter(arrayAdapter);
        layout.addView(listOfNotes);

        listOfNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note selectedNote = (Note) adapterView.getItemAtPosition(i);
                String noteTitle = selectedNote.getTitle();
                String noteCategory = selectedNote.getCategory();
                String noteContent = selectedNote.getContent();
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("noteTitle", noteTitle);
                intent.putExtra("noteCategory", noteCategory);
                intent.putExtra("noteContent", noteContent);
                arrayAdapter.notifyDataSetChanged();
                startActivityForResult(intent, 1);
                myNotes.remove(i);
            }
        });

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1)
        {
            Note newNote = new Note(data.getStringExtra("noteTitle"), data.getStringExtra("noteContent"), data.getStringExtra("noteCategory"));
            myNotes.add(newNote);
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
