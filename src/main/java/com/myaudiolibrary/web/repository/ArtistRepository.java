package com.myaudiolibrary.web.repository;

import com.myaudiolibrary.web.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {

    @Query("SELECT a FROM Artist a WHERE a.name LIKE %:name%")
    Page<Artist> findArtistByName(@Param("name") String name, Pageable pageable);

    Artist findByName(String name);
}
