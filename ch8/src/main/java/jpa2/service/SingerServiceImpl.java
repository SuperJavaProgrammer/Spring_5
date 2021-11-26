package jpa2.service;

import jpa2.entities.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Service("jpaSingerService") //компонент Spring, который предоставляет услуги бизнес-логики другому уровню
@Repository //в данном классе содержится логика доступа к данным
@Transactional //для определения требований к транзакциям
//@SuppressWarnings("unchecked")
public class SingerServiceImpl implements SingerService {
    final static String ALL_SINGER_NATIVE_QUERY = "select id, first_name, last_name, birth_date, version from singer"; //обычный запрос SQL

    private static Logger logger = LoggerFactory.getLogger(SingerServiceImpl.class);

    @PersistenceContext //для внедрения интерфейса EntityManager
    private EntityManager em;

    @Transactional(readOnly=true)
    @Override
    public List<Singer> findAll() {
        return em.createNamedQuery(Singer.FIND_ALL, Singer.class).getResultList(); //создать именованный запрос, возвращает класс Singer, списком
    }
    
    @Transactional(readOnly=true)
    @Override
    public List<Singer> findAllWithAlbum() {
        return em.createNamedQuery(Singer.FIND_ALL_WITH_ALBUM, Singer.class).getResultList();
    }

    @Transactional(readOnly=true)
    @Override
    public Singer findById(Long id) {
        TypedQuery<Singer> query = em.createNamedQuery(Singer.FIND_SINGER_BY_ID, Singer.class);
            query.setParameter("id", id); //установить параметр для запроса
        return query.getSingleResult(); //получить единственный ответ
    }

    @Override
    public Singer save(Singer singer) {
        if (singer.getId() == null) { //нет Id - значит это новый объект для добавления
            logger.info("Inserting new singer");
            em.persist(singer);
        } else { //если есть, то нужно обновить существующий
            logger.info("Updating existing singer");
            em.merge(singer);
        }
        logger.info("Singer saved with id: " + singer.getId());
        return singer;
    }

    @Override
    public void delete(Singer singer) {
        Singer mergedContact = em.merge(singer); //для объединения состояния сущности с текущим контекстом сохраняемости, возвращает управляемый экземпляр сущности певца
        em.remove(mergedContact);
        logger.info("Singer with id: " + singer.getId()  + " deleted successfully");
    }

    @Transactional(readOnly=true)
    @Override
    public List<Singer> findAllByNativeQuery() {
        return em.createNativeQuery(ALL_SINGER_NATIVE_QUERY, Singer.class).getResultList(); //использование родного запроса на SQL
    }

    @Transactional(readOnly=true)
    @Override
    public List<Singer> findAllByNativeQuerySQL() {
        return em.createNativeQuery(ALL_SINGER_NATIVE_QUERY, "singerResult").getResultList(); //второй вариант с использованием @SqlResultSetMapping
    }

    @Transactional(readOnly=true)
    @Override
    public List<Singer> findByCriteriaQuery(String firstName, String lastName) { //вернуть результат по особым критериям запроса, когда много разных параметров и что-то может быть, а чего-то нет
        logger.info("Finding singer for firstName: " + firstName + " and lastName: " + lastName);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Singer> criteriaQuery = cb.createQuery(Singer.class); //создается типизированный запрос
        Root<Singer> singerRoot = criteriaQuery.from(Singer.class); //получаем корневой объект запроса, основа для путевых выражений в запросе
            singerRoot.fetch(Singer_.albums, JoinType.LEFT); //немедленная выборка связей с альбомами, LEFT - внешнее соединение таблиц БД, аналог left join fetch
            singerRoot.fetch(Singer_.instruments, JoinType.LEFT); //и инструментами
        criteriaQuery.select(singerRoot).distinct(true); //передача корневого объекта запроса в качестве результирующего типа, исключить дублирование

        Predicate criteria = cb.conjunction(); //было объединено несколько ограничений

        if (firstName != null) {
            Predicate p = cb.equal(singerRoot.get(Singer_.firstName), firstName); //новый экземпляр предиката на равенство, если было введено firstName
            criteria = cb.and(criteria, p); //объединение условий
        }
        if (lastName != null) { //аналогично добавляется lastName, если он есть
            Predicate p = cb.equal(singerRoot.get(Singer_.lastName), lastName);
            criteria = cb.and(criteria, p);
        }

        criteriaQuery.where(criteria); //передача запросу

        return em.createQuery(criteriaQuery).getResultList(); //получение результата
    }

}
