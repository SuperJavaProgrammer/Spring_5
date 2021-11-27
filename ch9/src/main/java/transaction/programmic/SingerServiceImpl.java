package transaction.programmic;

import transaction.entities.Singer;
import transaction.repos.SingerRepository;
import transaction.services.SingerService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//@Service("singerService")
//@Repository
public class SingerServiceImpl implements SingerService {

	@Autowired
	private SingerRepository singerRepository;
	@Autowired
	private TransactionTemplate transactionTemplate;
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Singer> findAll() {
		return Lists.newArrayList(singerRepository.findAll());
	}

	@Override
	public Singer findById(Long id) {
		return singerRepository.findById(id).get();
	}

	@Override
	public Singer save(Singer singer) {
		return singerRepository.save(singer);
	}

	@Override
	public long countAll() {
		return transactionTemplate.execute(transactionStatus -> em.createNamedQuery(Singer.COUNT_ALL, Long.class).getSingleResult()); //TransactionStatus ts - позволяет диспетчеру Т управлять выполнением Т
	}
}
