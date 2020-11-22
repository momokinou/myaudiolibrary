package com.myaudiolibrary.web.exception;

public class AlbumException extends Throwable {
    public static final String ID = "L'identifiant passé ne correspond à aucun album.";

    public AlbumException(String message){
        super(message);
        System.out.println(this.getMessage());
    }
}
