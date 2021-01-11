package com.example.ghichuhigo.object;

import java.io.Serializable;

public class Note implements Serializable {

    private int ID;
    private String Title;
    private String Content;

    public Note() {
    }

    public Note(String title, String content) {
        Title = title;
        Content = content;
    }

    public Note(int id, String title, String content) {
        ID = id;
        Title = title;
        Content = content;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
