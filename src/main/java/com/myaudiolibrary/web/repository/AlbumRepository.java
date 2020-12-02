package com.myaudiolibrary.web.repository;

import com.myaudiolibrary.web.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Album a WHERE a.artist.id = :id")

    void deleteByArtistID(@Param("id")Integer id);

    Album findByTitle(String title);
}
