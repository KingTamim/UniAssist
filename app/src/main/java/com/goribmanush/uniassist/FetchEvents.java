package com.goribmanush.uniassist;

/**
 * Created by Tamim on 7/27/2017.
 */

public class FetchEvents {

    private String title;
    private  String description;

    public FetchEvents() {
    }

    public FetchEvents(String title, String description) {
        this.title = title;
        this.description = description;
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
}
