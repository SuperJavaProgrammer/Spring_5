package transaction.services;

import transaction.entities.Singer;
import transaction.repos.SingerRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("singerService")
//Транзакции: локальные, глобальные - охватывают многие ресурсы сервера БД
//В глобальной Т принимают участие 4 стороны: сервер БД, диспетчер ресурсов, диспетчер транзакций, приложение
//4 свойства транзакций ACID: атомарность, согласованность, изолированность, долговечность
@Transactional //по умолчанию Т должна быть распространена, выбран уровень изоляции, время ожидания по умолчанию, режим чтения и записи
//propagation = Propagation.REQUIRED(0),SUPPORTS(1),MANDATORY(2),REQUIRES_NEW(3),NOT_SUPPORTED(4),NEVER(5),NESTED(6) режимы распространения
//isolation = Isolation.DEFAULT(-1), READ_UNCOMMITTED(1), READ_COMMITTED(2), REPEATABLE_READ(4), SERIALIZABLE(8) уровень изоляции
//timeout = время ожидания в сек.
//readOnly = false/true
//rollbackFor = классы исключений, для которых будет произведен откат Т
//rollbackForClassName =
//noRollbackFor = классы исключений, для которых не будет произведен откат Т
//noRollbackForClassName =
//value = значение описателя указанной транзакции
	public class SingerServiceImpl implements SingerService {

	@Autowired
	private SingerRepository singerRepository;

	@Override
	@Transactional(readOnly = true) //переопределяет аннотацию на уровне класса, не изменяя ни одного отрибута, но устанавливая Т в режим доступа только для чтения
	public List<Singer> findAll() {
		return Lists.newArrayList(singerRepository.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public Singer findById(Long id) {
		return singerRepository.findById(id).get();
	}

	@Override
	public Singer save(Singer singer) {
		return singerRepository.save(singer);
	}

	@Override
	@Transactional(propagation = Propagation.NEVER) //не нужно вообще привлекать Т
	public long countAll() {
		return singerRepository.countAllSingers();
	}
}

