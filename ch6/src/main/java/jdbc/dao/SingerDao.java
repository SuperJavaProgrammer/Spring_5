package jdbc.dao;

import jdbc.entities.Singer;

import java.util.List;

public interface SingerDao { //описывает возможные операции с объектами Singer
	List<Singer> findAll();
	List<Singer> findByFirstName(String firstName);
	String findNameById(Long id);
	String findLastNameById(Long id);
	String findFirstNameById(Long id);
	List<Singer> findAllWithAlbums();
	void insert(Singer singer);
	void update(Singer singer);
	void delete(Long singerId);
	void insertWithAlbum(Singer singer);
}

