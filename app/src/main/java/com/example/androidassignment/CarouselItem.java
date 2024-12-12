package com.example.androidassignment;

public class CarouselItem {
    private String imageUrl;
    private String title;
    private String subtitle;

    public CarouselItem(String imageUrl, String title, String subtitle) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}

