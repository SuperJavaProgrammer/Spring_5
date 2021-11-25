package hibernate.dao;

import hibernate.entities.Singer;

import java.util.List;

public interface SingerDao { //операции для работы с Первцом
	List<Singer> findAll();
	List<Singer> findAllWithAlbum();
	Singer findById(Long id);
	Singer save(Singer singer);
	void delete(Singer singer);
}
