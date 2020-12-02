//package com.myaudiolibrary.web.controller;
//
//import com.myaudiolibrary.web.model.Album;
//import com.myaudiolibrary.web.repository.AlbumRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import javax.persistence.EntityExistsException;
//
//
//@CrossOrigin
//@RestController
//@RequestMapping(value = "/albums")
//public class AlbumController {
//
//    @Autowired
//    private AlbumRepository albumRepository;
//
//    //Ajout d'un album
//    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Album createAlbum(@RequestBody Album album){
//        //erreur 409 si Title existe déjà
//        //l'ajout d'un album déjà existant ou vide provoquera cette excpetion
//        //le nom du nouvel album sera présenté comme créé sur le front, mais cela est un bug venant du front
//        //rafraîchir la page permet de voir correctement l'annulation de la requête
//        if(albumRepository.findByTitle(album.getTitle()) != null){
//            throw new EntityExistsException("L'album " + album.getTitle() + " existe déjà");}
//        return albumRepository.save(album);
//    }
//
//    //Suppression d'un album
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteAlbum(@PathVariable Integer id){
//        albumRepository.deleteById(id);
//    }
//
//}
