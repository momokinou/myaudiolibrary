package com.myaudiolibrary.web.model;

import javax.persistence.*;

@Entity
@Table(name = "album")
public class Album {

    @Id
    private Long id;

    private String title;

    public Album(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Album() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
