package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/artists")
public class ArtistController {
    @Autowired
    ArtistRepository artistRepository;

    //Afficher un artiste
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Artist findbyid(@PathVariable Integer id){
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
        return artistRepository.findByName(name, pageRequest);
    }

    public Page<Artist> listArtists(){
        return null;
    }

}
