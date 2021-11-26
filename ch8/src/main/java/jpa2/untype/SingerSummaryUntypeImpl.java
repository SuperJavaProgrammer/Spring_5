package jpa2.untype;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Iterator;
import java.util.List;

@Service("singerSummaryUntype")
@Repository
@Transactional
public class SingerSummaryUntypeImpl {

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public void displayAllSingerSummary() { //запрос нетипизированных результатов, аналог SingerSummaryServiceImpl, только не используется специальный для этого ответа класс SingerSummary
		List result = em.createQuery(
				"select s.firstName, s.lastName, a.title from Singer s "
						+ "left join s.albums a "
						+ "where a.releaseDate=(select max(a2.releaseDate) from Album a2 where a2.singer.id = s.id)")
				.getResultList(); //просто получаем обобщенный List
		int count = 0;
		for (Iterator i = result.iterator(); i.hasNext();) { //а потом отдельно перебираем все его объекты
			Object[] values = (Object[]) i.next();
			System.out.println("Номер " + ++count + ": " + values[0] + ", " + values[1] + ", " + values[2]); //и извлекаем полученные значения
		}
	}
}
