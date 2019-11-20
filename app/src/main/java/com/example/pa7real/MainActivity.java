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
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "SQLiteFunTag";
    GridLayout layout;
    ListView noteListView;
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
        if (intent.getStringExtra("noteTitle") != null){
            Note newNote = new Note(intent.getStringExtra("noteTitle"), intent.getStringExtra("noteContent"), intent.getStringExtra("noteCategory"));
            createListView(newNote);
        } else
            createListView(null);

        // enable multiple selection on list view
        noteListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        // set the listener for entering CAM (contextual action mode)
        // user long presses they can select multiple items
        noteListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            ArrayList<Note> selectedNotes = new ArrayList<>();
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                int numChecked = noteListView.getCheckedItemCount();
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
                        SparseBooleanArray selected = noteListView.getCheckedItemPositions();
                        ArrayList<Note> allNotes = new ArrayList<>(openHelper.getSelectAllNotesList());
                        Cursor cursor = openHelper.getSelectAllNotesCursor();
                        // Captures all selected ids with a loop
                        int k = 0;
                        for (int j = 0; j <= allNotes.size(); j++) {
                            Log.d(TAG, "valueOfI " + j);
                            if (j == selected.keyAt(k)) {
                                Log.d(TAG, "selectedNote: " + selected.keyAt(j));
                                Note selecteditem = (Note) allNotes.get(j);
                                Log.d(TAG, "valueOfSelectedArray: " + selected.toString());
                                Log.d(TAG, "beforeAddingNote: " + selecteditem.getTitle());
                                // Remove selected items following the ids
                                selectedNotes.add(selecteditem);
                                Log.d(TAG, "afterAddingNote: " + selecteditem.getTitle());
                                k++;
                            }
                        }
                        for (int i = 0; i < selectedNotes.size(); i++){
                            openHelper.delete(selectedNotes.get(i).getId());
                        }
                        cursor = openHelper.getSelectAllNotesCursor();
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
        noteListView = new ListView(this);
        GridLayout.Spec rowSpec = GridLayout.spec(1, 1, 1);
        // row start index, row span, row weight
        GridLayout.Spec colSpec = GridLayout.spec(0, 1, 1);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, colSpec);
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        noteListView.setLayoutParams(layoutParams);
        NoteOpenHelper openHelper = new NoteOpenHelper(this);

        Cursor cursor = openHelper.getSelectAllNotesCursor();
        cursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.personal_activity_layout_file,
                cursor,
                // parallel arrays... names of columns to get data FROM
                new String[] {NoteOpenHelper.TITLE, NoteOpenHelper.IMAGE_REP},
                // ids of textviews to put the data IN
                new int[] {android.R.id.text1, android.R.id.icon},
                0 // leave default
        );

        noteListView.setAdapter(cursorAdapter);
        layout.addView(noteListView);

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            noteListView.setAdapter(cursorAdapter);
        }
    }
}
