package com.myaudiolibrary.web.model;

import javax.persistence.*;

@Entity
@Table(name = "album")
public class Album {

    //attributs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AlbumId")
    private Integer id;
    @Column(name = "Title")
    private String title;
    @ManyToOne
    @JoinColumn(name = "ArtistId")
    private Artist artist;

    //constructeurs
    public Album() {}

    public Album(Integer id, String title, Artist artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    //getters & setters
    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
