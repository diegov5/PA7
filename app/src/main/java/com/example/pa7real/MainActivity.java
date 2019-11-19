/*
 * This program creates a note taking application that allows you to create new notes, edit them, and delete them
 *
 * CPSC 312-01, Fall 2019
 * Programming Assignment #6
 *
 * @authors Diego Valdez:       Handled the Note object and most of the integration of the MVC data model for the entire app
 *          Patrick Seminatore: Did most of the work with setting up the layout through java without XML
 * <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 * <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 * <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 * <div>Icons made by <a href="https://www.flaticon.com/authors/mynamepong" title="mynamepong">mynamepong</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 * @version v1.0 10/22/19
 */

package com.example.pa7real;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button newNoteButton;
    GridLayout layout;
    ListView listOfNotes;
    // ArrayAdapter<Note> cursorAdapter;
    SimpleCursorAdapter cursorAdapter;

    /*
     *   This method handles creating the root grid layout, and calling the methods necessary
     *      for creating the rest of the widgets on the page
     *
     *   Parameters: savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new GridLayout(MainActivity.this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setContentView(layout);
        layout.setColumnCount(1);
        layout.setRowCount(2);

        // createNewNoteButton();
        final NoteOpenHelper openHelper = new NoteOpenHelper(this);

        Intent intent = getIntent();
        //if (intent.getStringExtra("noteTitle") != null){
        //    Note newNote = new Note(intent.getStringExtra("noteTitle"), intent.getStringExtra("noteContent"), intent.getStringExtra("noteCategory"));
        //    createListView(newNote);
        //} else
        //    createListView(null);

        // enable multiple selection on list view
        listOfNotes.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        // set the listener for entering CAM (contextual action mode)
        // user long presses they can select multiple items
        listOfNotes.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                int numChecked = listOfNotes.getCheckedItemCount();
                actionMode.setTitle(numChecked + " selected");
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.cam_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.deleteMenuItem:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        SparseBooleanArray selected = listOfNotes.getCheckedItemPositions();
                        // Captures all selected ids with a loop
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                //Note selecteditem = selected.
                                // Remove selected items following the ids
                                //openHelper.delete(selecteditem.getId());
                            }
                        }
                        Cursor cursor = openHelper.getSelectAllNotesCursor();
                        cursorAdapter.swapCursor(cursor);
                        actionMode.finish();
                        return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
    }

    /*
     *   This method handles creating the button that sends you to the activity to create a new note
     *
     *
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
    */
    // inflate the menu in main_menu.xml
    // override a method to do this

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // override a callback that executes when the user presses a menu action

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.addMenuItem:
                onClick();
                return true; // we consumed/handled the event
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
     *   This method handles creating the list view of the note list view, and giving them the proper
     *      functionality for on click and on long clicks
     *
     *   Parameters: savedInstanceState
     */
    public void createListView(Note newNote){
        listOfNotes = new ListView(this);
        GridLayout.Spec rowSpec = GridLayout.spec(1, 1, 1);
        // row start index, row span, row weight
        GridLayout.Spec colSpec = GridLayout.spec(0, 1, 1);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, colSpec);
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        listOfNotes.setLayoutParams(layoutParams);
        NoteOpenHelper openHelper = new NoteOpenHelper(this);

        Cursor cursor = openHelper.getSelectAllNotesCursor();
        cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.activity_list_item,
                cursor,
                // parallel arrays... names of columns to get data FROM
                new String[] {NoteOpenHelper.TITLE, NoteOpenHelper.IMAGE_REP},
                // ids of textviews to put the data IN
                new int[] {android.R.id.text1, android.R.id.icon},
                0 // leave default
        );
        /*
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
                                cursorAdapter.notifyDataSetChanged();
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
        */

        listOfNotes.setAdapter(cursorAdapter);
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
                cursorAdapter.notifyDataSetChanged();
                startActivityForResult(intent, 1);
                // myNotes.remove(i);
            }
        });

    }

    /*
     *   This method handles the functionality for creating a new note
     *
     *   Parameters: view
     */
    public void onClick() {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivityForResult(intent, 1);
    }

    /*
     *   This method handles creating the root grid layout, and calling the methods necessary
     *      for creating the rest of the widgets on the page
     *
     *   Parameters: request code: the identifier we send to see if we achieved the needed result
     *               result code: the identifier we receive to see if we achieved the needed result
     *               date: the intent that we send in to the new activity we are starting for a result
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1)
        {
            NoteOpenHelper openHelper = new NoteOpenHelper(this);
            Note newNote = new Note(data.getStringExtra("noteTitle"), data.getStringExtra("noteContent"), data.getStringExtra("noteCategory"));
            openHelper.insertContact(newNote);
            Cursor cursor = openHelper.getSelectAllNotesCursor();
            cursorAdapter.swapCursor(cursor);
        }
    }
}
