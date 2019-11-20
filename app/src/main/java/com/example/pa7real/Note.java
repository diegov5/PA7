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

public class Note {
    private int id;
    private String title;
    private String content;
    private String category;
    private int imageRep;

    /*
    * EVC for class
    *
    * Parameters: int id
    *             String title
    *             String content
    *             String category
    */
    Note(int id, String title, String content, String category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        if (category.equals("School"))
            this.imageRep = R.drawable.classroom;
        if (category.equals("Personal"))
            this.imageRep = R.drawable.user;
        if (category.equals("Work"))
            this.imageRep = R.drawable.man_desk;
        if (category.equals("Other"))
            this.imageRep = R.drawable.planning;
    }

    /*
     * EVC for class, without an id
     *
     * Parameters: String title
     *             String content
     *             String category
     */
    Note(String title, String content, String category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        if (category.equals("School"))
            this.imageRep = R.drawable.classroom;
        if (category.equals("Personal"))
            this.imageRep = R.drawable.user;
        if (category.equals("Work"))
            this.imageRep = R.drawable.man_desk;
        if (category.equals("Other"))
            this.imageRep = R.drawable.planning;
    }

    /*
     *  toString method needed so that the list view shows the title of the note
     *
     *  Return: A string, title, to be displayed in the listview
     */
    @Override
    public String toString() {
        return this.title;
    }

    /*
     *  toString method needed so that the list view shows the title of the note
     *
     *  Return: A string, title
     */
    String getTitle() {
        return title;
    }

    /*
     *  gets the content string of a note
     *
     *  Return: A string, content
     */
    String getContent() {
        return content;
    }

    /*
     *  gets the string that was chosen in the spinner
     *
     *  Return: A string, category
     */
    String getCategory() {
        return category;
    }

    /*
     *  gets the id of a note
     *
     *  Return: An int, the id
     */
    int getId() {
        return id;
    }

    /*
     *  gets the image representation of a category
     *
     *  Return: An int, the id of the drawable
     */
    int getImageRep() {
        return imageRep;
    }
}
