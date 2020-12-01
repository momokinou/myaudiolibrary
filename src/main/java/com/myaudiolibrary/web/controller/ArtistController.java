package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.AlbumRepository;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.aspectj.util.GenericSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@CrossOrigin
@RestController
@RequestMapping(value = "/artists")
public class ArtistController {
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    AlbumRepository albumRepository;

    //Afficher un artiste
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Artist findbyid(@PathVariable Integer id){
        //erreur 404 si l'artiste n'existe pas
        if (artistRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException("L'artiste avec comme id " + id + "n'a pas été trouvé");

        }
        return artistRepository.findById(id).get();
    }

    //Recherche par nom (avec Page)
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = "application/json",
            params = {"name", "page", "size", "sortProperty", "sortDirection"})
    public Page<Artist> findArtistByName(@RequestParam (value = "name") String name,
                                         @RequestParam (value = "page", defaultValue = "0") Integer page,
                                         @RequestParam (value = "size", defaultValue = "10") Integer size,
                                         @RequestParam (value = "sortProperty") String sortProperty,
                                         @RequestParam (value = "sortDirection") String sortDirection){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        Page<Artist> artists = artistRepository.findArtistByName(name, pageRequest);
//        if (!artists.hasContent()){
//            return null;
//        }
        return artists;
    }

    //Liste des artistes
    @GetMapping(
            value = "",
            produces = "application/json",
            params = {"page", "size", "sortProperty", "sortDirection"})
    public Page<Artist> listArtists(@RequestParam (value = "page", defaultValue = "0") Integer page,
                                    @RequestParam (value = "size", defaultValue = "10") Integer size,
                                    @RequestParam (value = "sortProperty") String sortProperty,
                                    @RequestParam (value = "sortDirection") String sortDirection){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        return artistRepository.findAll(pageRequest);
    }

    //Création d'un artiste
    @PostMapping(
            value = "",
            produces = "application/json",
            consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Artist newArtist(@RequestBody Artist artist) {
//        if(!artistRepository.findByName(artist.getName()).getName().isEmpty()){
//            throw new EntityExistsException("L'artiste existe déjà");
//        }
        return artistRepository.save(artist);
    }

    //Modification d'un artiste
    @PutMapping(
            value = "/{id}",
            consumes = "application/json",
            produces = "application/json")
    public Artist updateArtist(@PathVariable Integer id, @RequestBody Artist artist){
        return artistRepository.save(artist);
    }

    //Suppression d'un artiste
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Integer id) {
        albumRepository.deleteByArtistID(id);
        artistRepository.deleteById(id);
    }

}
