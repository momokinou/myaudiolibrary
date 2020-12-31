package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.repository.AlbumRepository;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityExistsException;
import javax.persistence.SynchronizationType;
import java.util.Optional;


@Controller
@RequestMapping(value = "/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    //Ajout d'un album
    @PostMapping(value = "", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView createAlbum(Album album, final ModelMap model){
        //erreur 409 si Title existe déjà
        //l'ajout d'un album déjà existant ou vide provoquera cette excpetion
        //le nom du nouvel album sera présenté comme créé sur le front, mais cela est un bug venant du front
        //rafraîchir la page permet de voir correctement l'annulation de la requête
        if(albumRepository.findByTitle(album.getTitle()) != null){
            throw new EntityExistsException("L'album " + album.getTitle() + " existe déjà");}

        albumRepository.save(album);
        return new RedirectView("/artists/"  + album.getArtist().getId());
    }

    //Suppression d'un album
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RedirectView deleteAlbum(@PathVariable Integer id){
        Optional<Album> album = albumRepository.findById(id);
        if (album.isPresent()) {
        albumRepository.deleteById(id);
        }
        return new RedirectView("/artists/"  + album.get().getArtist().getId());
    }

}
