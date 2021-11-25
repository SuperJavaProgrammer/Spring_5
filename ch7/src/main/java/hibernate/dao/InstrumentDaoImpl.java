package hibernate.dao;

import hibernate.entities.Instrument;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional //использовать транзакции
@Repository("instrumentDao") //компонент Spring, реализующий логику работы с БД
public class InstrumentDaoImpl implements InstrumentDao { //класс реализации методов работы с Инструментами

	private static final Log logger = LogFactory.getLog(InstrumentDaoImpl.class);
	private SessionFactory sessionFactory;

	@Override
    public Instrument save(Instrument instrument) {
		sessionFactory.getCurrentSession().saveOrUpdate(instrument); //вызов метода сессии для сохранения данных
		logger.info("Instrument saved with id: " + instrument.getInstrumentId());
		return instrument;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource(name = "sessionFactory") //внедрить данные по сессии
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
