package jpa2.сrudJpaRepository;

import jpa2.entities.Album;
import jpa2.entities.Singer;

import java.util.List;

public interface AlbumService {
	List<Album> findBySinger(Singer singer);
	List<Album> findByTitle(String title);
}
