package webmvc.repos;

import webmvc.entities.Singer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SingerRepository extends PagingAndSortingRepository<Singer, Long> { //более совершенное расширение CrudRepository с сортировкой
}
