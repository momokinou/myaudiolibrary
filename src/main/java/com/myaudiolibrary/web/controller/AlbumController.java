package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.exception.AlbumException;
import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping(value = "/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Album createAlbum(@RequestBody Album album){
        return albumRepository.save(album);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable Integer id) throws AlbumException {
        if (id == null){
            throw new AlbumException("L'album correspondant à l'identifiant " + id + " n'a pas été trouvé");
        }
        albumRepository.deleteById(id);
    }

}
