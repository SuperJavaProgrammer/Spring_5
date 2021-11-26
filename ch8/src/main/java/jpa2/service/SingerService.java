package jpa2.service;

import jpa2.entities.Singer;

import java.util.List;

public interface SingerService {
    List<Singer> findAll();
    List<Singer> findAllWithAlbum();
    Singer findById(Long id);
    Singer save(Singer singer);
    void delete(Singer singer);
    List<Singer> findAllByNativeQuery();
    List<Singer> findAllByNativeQuerySQL();
    List<Singer> findByCriteriaQuery(String firstName, String lastName);
}
