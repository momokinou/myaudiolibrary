package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.AlbumRepository;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityExistsException;

@Controller
@RequestMapping(value = "/artists")
public class ArtistController {
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    AlbumRepository albumRepository;

    //Afficher un artiste
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String findById(@PathVariable ("id") Integer id, final ModelMap model){
        //erreur 404 si l'artiste n'existe pas
        /*if (artistRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException("L'artiste avec comme id " + id + "n'a pas été trouvé");
        }*/
        model.put("artist", artistRepository.findById(id).get());
        return "detailArtist";
    }

    //Recherche par nom (avec Page)
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            params = {"name", "page", "size", "sortProperty", "sortDirection"})
    public String findArtistByName(@RequestParam (value = "name") String name,
                                   @RequestParam (value = "page", defaultValue = "0") Integer page,
                                   @RequestParam (value = "size", defaultValue = "10") Integer size,
                                   @RequestParam (value = "sortProperty") String sortProperty,
                                   @RequestParam (value = "sortDirection") String sortDirection,
                                   final ModelMap model){
        System.out.print(name);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        Page<Artist> artists = artistRepository.findArtistByName(name, pageRequest);


        //gérer l'erreur lorsque que l'on accède à la dernière page
        if (artists.getNumberOfElements() == 0){
            throw new IllegalArgumentException("Le paramètre page ne peut pas être supérieur au nombre total de page !");
        }
        model.put("artist", artists);
        return "listeArtists";
    }

    //Liste des artistes
    @GetMapping(
            value = "",
            produces = "application/json",
            params = {"page", "size", "sortProperty", "sortDirection"})
    public String listArtists(@RequestParam (value = "page", defaultValue = "0") Integer page,
                              @RequestParam (value = "size", defaultValue = "10") Integer size,
                              @RequestParam (value = "sortProperty") String sortProperty,
                              @RequestParam (value = "sortDirection") String sortDirection,
                              final ModelMap model){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        //gérer les erreurs sur les valeurs de pageRequest
        if (page < 0){
            //400
            throw new IllegalArgumentException("Le paramètre page doit être positif ou nul !");
        }
        //gérer l'erreur lorsque que l'on accède à la dernière page
        if (artistRepository.findAll(pageRequest).getNumberOfElements() == 0){
            throw new IllegalArgumentException("Le paramètre page ne peut pas être supérieur au nombre total de page !");
        }
        if (size <= 0){
            throw new IllegalArgumentException("Le paramètre size doit être compris entre 0 et 50 !");
        }
        if (size > 50){
            throw new IllegalArgumentException("Le paramètre size doit être compris entre 0 et 50 !");
        }
        if (!"ASC".equalsIgnoreCase(sortDirection) && !"DESC".equalsIgnoreCase(sortDirection)){
            throw new IllegalArgumentException("Le paramètre sortDirection doit valoir ASC ou DESC !");
        }
        model.put("artist", artistRepository.findAll(pageRequest));
        model.put("pageNumber", page +1);
        model.put("previousPage", page -1);
        model.put("nextPage", page + 1);
        model.put("end", (page) * size + artistRepository.findAll(pageRequest).getNumberOfElements());
        return "listeArtists";
    }

    //Création d'un artiste
    @PostMapping(
            value = "/newArtist",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView newArtist(Artist artist, final ModelMap model) {
        //erreur 409 si name existe déjà
        if(artistRepository.findByName(artist.getName()) != null){
            throw new EntityExistsException("L'artiste " + artist.getName() + " existe déjà");
        }
        artist = artistRepository.save(artist);
        return new RedirectView("/artists/" + artist.getId());
    }

    //Modification d'un artiste
    //après la sauvergarde de la modification, le front n'affiche plus ses albums. Mais ils existent toujours
    //rafraîchir la page permet de voir comme attendu la modification du nom de l'artiste avec ses albums
    @PutMapping(value = "/{id}")
    public RedirectView updateArtist(@PathVariable Integer id, Artist artist){
        artist = artistRepository.save(artist);
        return new RedirectView("/artists/" + artist.getId());
    }

    //Suppression d'un artiste
    @DeleteMapping(value = "/{id}")
    public RedirectView deleteArtist(@PathVariable Integer id) {
        albumRepository.deleteByArtistID(id);
        artistRepository.deleteById(id);
        return new RedirectView("/artists?page=0&size=10&sortProperty=name&sortDirection=ASC");
    }

}
