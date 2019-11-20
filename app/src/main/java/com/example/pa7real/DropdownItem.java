/*
 * This program creates a note taking application that allows you to create new notes, edit them, and delete them, and store them
 *          in a SQLite database
 *
 * CPSC 312-01, Fall 2019
 * Programming Assignment #7
 *
 * @authors Diego Valdez:       Handled the database management, UI work, and custom layouts
 *          Patrick Seminatore: Worked on the deletion of the notes, manipulating the listview, and editing existing notes
 * <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 * <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 * <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 * <div>Icons made by <a href="https://www.flaticon.com/authors/mynamepong" title="mynamepong">mynamepong</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 * @version v1.0 11/19/19
 */

package com.example.pa7real;

public class DropdownItem {
    String categoryName;
    int iconID;

    public DropdownItem(String categoryName, int iconID) {
        this.categoryName = categoryName;
        this.iconID = iconID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getIconID() {
        return iconID;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
