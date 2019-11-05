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

public class Note {
    private String title;
    private String content;
    private String category;

    /*
     EVC for class
     *
     * Parameters: String title
     *             String content
                   String category
     */
    Note(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
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
}
