package com.example.notes;

import java.util.Date;

public class NotesModel {
    String title, content, createdOn;
    long modifiedOn;
    int id;
    public NotesModel(int id, String title, String content, String createdOn, long modifiedOn) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }
    public NotesModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public long getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(long modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
}
