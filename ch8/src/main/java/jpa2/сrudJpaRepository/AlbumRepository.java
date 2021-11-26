package jpa2.сrudJpaRepository;

import jpa2.entities.Album;
import jpa2.entities.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> { //расширение JpaRepository
	List<Album> findBySinger(Singer singer);

	@Query("select a from Album a where a.title like %:title%") //специальные запросы Spring Data JPA
    List<Album> findByTitle(@Param("title") String t); //если бы значение параметра было title, а не t, то @Param не нужен
}
