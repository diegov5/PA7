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
