package com.goribmanush.uniassist;

/**
 * Created by Tamim on 7/27/2017.
 */

public class FetchNews {

    private String title;
    private String description;
    private String Image;

    public FetchNews() {
    }

    public FetchNews(String title, String description, String image) {
        this.title = title;
        this.description = description;
        Image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
