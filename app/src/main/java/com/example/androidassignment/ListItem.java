package com.example.androidassignment;

import android.widget.ImageView;

public class ListItem {
    private int imageUrl;
    private String title;
    private String subtitle;

    public ListItem(int imageUrl, String title, String subtitle) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.subtitle = subtitle;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}

