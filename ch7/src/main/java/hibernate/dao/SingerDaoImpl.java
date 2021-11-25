package hibernate.dao;

import hibernate.entities.Singer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("unchecked")
@Transactional
@Repository("singerDao")
public class SingerDaoImpl implements SingerDao {
	private static final Log logger = LogFactory.getLog(SingerDaoImpl.class);
	private SessionFactory sessionFactory;

	@Transactional(readOnly = true) //транзакция только для чтения для повышения производительности
	public List<Singer> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from Singer s").list(); //оператор HQL, list() для возврата списка результатов
	}

	@Transactional(readOnly = true)
	public List<Singer> findAllWithAlbum() {
		return sessionFactory.getCurrentSession().getNamedQuery("Singer.findAllWithAlbum").list(); //получить именованный запрос(определен в классе Singer)
	}

	@Transactional(readOnly = true)
	public Singer findById(Long id) {
		return (Singer) sessionFactory.getCurrentSession().getNamedQuery("Singer.findById").setParameter("id", id).uniqueResult(); //именованный запрос с установкой параметра. uniqueResult() для возврата одного результатат
	}

	public Singer save(Singer singer) {
		sessionFactory.getCurrentSession().saveOrUpdate(singer); //saveOrUpdate для сохранения/обновления
		logger.info("Singer saved with id: " + singer.getId());
		return singer;
	}

	public void delete(Singer singer) {
		sessionFactory.getCurrentSession().delete(singer); //delete для удаления
		logger.info("Singer deleted with id: " + singer.getId());
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired //внедрение фабрики сеансов
//	@Qualifier("sessionFactory")
//	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
