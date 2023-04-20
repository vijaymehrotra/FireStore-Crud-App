package com.example.firestorecrud;

public class Model {
    String id,title,disc;

    public Model() {
    }

    public Model(String id, String title, String disc) {
        this.id = id;
        this.title = title;
        this.disc = disc;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDisc() {
        return disc;
    }
}
