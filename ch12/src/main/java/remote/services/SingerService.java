package remote.services;

import remote.entities.Singer;

import java.util.List;

public interface SingerService {
	List<Singer> findAll();
	List<Singer> findByFirstName(String firstName);
	Singer findById(Long id);
	Singer save(Singer singer);
	void delete(Singer singer);
}
