package jdbc.dao;

import jdbc.sqlUpdate.InsertSingerAlbum;
import jdbc.StoredFunctionFirstNameById;
import jdbc.entities.Album;
import jdbc.entities.Singer;
import jdbc.mappingSqlQuery.SelectAllSingers;
import jdbc.mappingSqlQuery.SelectSingerByFirstName;
import jdbc.sqlUpdate.DeleteSinger;
import jdbc.sqlUpdate.InsertSinger;
import jdbc.sqlUpdate.UpdateSinger;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Repository("singerDao")
public class JdbcSingerDao implements SingerDao, InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(JdbcSingerDao.class);
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate; //1 вариант, без использования именованных запросов
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate; //2 вариант, с использованием именованных запросов
	private SelectAllSingers selectAllSingers;
	private SelectSingerByFirstName selectSingerByFirstName;
	private UpdateSinger updateSinger;
	private InsertSinger insertSinger;
	private InsertSingerAlbum insertSingerAlbum;
	private DeleteSinger deleteSinger;
	private StoredFunctionFirstNameById storedFunctionFirstNameById;

	public DataSource getDataSource() {
		return dataSource;
	}

	@Override
	public String findLastNameById(Long id) {
		throw new NotImplementedException("findLastNameById");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (jdbcTemplate == null && namedParameterJdbcTemplate == null) {
			throw new BeanCreationException("Null JdbcTemplate and namedParameterJdbcTemplate on SingerDao");
		}
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public void setSelectAllSingers(SelectAllSingers selectAllSingers) {
		this.selectAllSingers = selectAllSingers;
	}

//	@Resource(name = "dataSource")
	@Autowired
//	@Qualifier("dataSource")
	public void setDataSource(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
			jdbcTemplate.setDataSource(dataSource);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

		this.dataSource = dataSource;
		this.selectAllSingers = new SelectAllSingers(dataSource);
		this.selectSingerByFirstName = new SelectSingerByFirstName(dataSource);
		this.updateSinger = new UpdateSinger(dataSource);
		this.insertSinger = new InsertSinger(dataSource);
		this.storedFunctionFirstNameById = new StoredFunctionFirstNameById(dataSource);
		this.deleteSinger = new DeleteSinger(dataSource);
	}

	@Override //1 вариант
    public String findNameById(Long id) {
		return jdbcTemplate.queryForObject( //вернуть результат, используя jdbcTemplate
				"select first_name || ' ' || last_name from singer where id = ?", //запроса
				new Object[]{id}, String.class); //передав массив значений в запросе по порядку. Возвращается тип String
	}

	public String findNameByIdNamedParameter(Long id) { //2 вариант
		Map<String, Object> namedParameters = new HashMap<>(); //в namedParameters заполнить параметры запроса
			namedParameters.put("singerId", id); //сопоставляя ключ и значение
		return namedParameterJdbcTemplate.queryForObject( //вернуть результат, используя namedParameterJdbcTemplate
				"SELECT first_name ||' '|| last_name FROM singer WHERE id = :singerId", //запроса
				namedParameters, String.class); //передав Map в запросе. Возвращается тип String
	}

	@Override
	public List<Singer> findAll() { //извлечение объектов с помощью RowMapper
		String sql = "select id, first_name, last_name, birth_date from singer";
		return namedParameterJdbcTemplate.query(sql,
				(rs, rowNum) -> new Singer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getDate("birth_date")));
	}

    public List<Singer> findAllMappingSqlQuery() {
	    return selectAllSingers.execute(); //вернуть результат, используя MappingSqlQuery
    }

	@Override
	public List<Singer> findAllWithAlbums() { //извлечение вложенных объектов с помощью ResultSetExtractor
		String sql = "SELECT s.id, s.first_name, s.last_name, s.birth_date" +
				", a.id AS album_id, a.title, a.release_date FROM singer s " +
				"LEFT JOIN album a ON s.id = a.singer_id";
		return namedParameterJdbcTemplate.query(sql, rs -> {
			Map<Long, Singer> map = new HashMap<>();
			Singer singer;
			while (rs.next()) {
				Long id = rs.getLong("id");
				singer = map.get(id);
				if (singer == null) {
					singer = new Singer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getDate("birth_date"), new ArrayList<>());
					map.put(id, singer);
				}
				Long albumId = rs.getLong("album_id");
				if (albumId > 0)
					singer.getAlbums().add(new Album(albumId, id, rs.getString("title"), rs.getDate("release_date")));
			}
			return new ArrayList<>(map.values());
		});
	}

	@Override
	public List<Singer> findByFirstName(String firstName) {
		Map<String, Object> paramMap = new HashMap<>(); //заполнить именованные параметры
			paramMap.put("first_name", firstName);
		return selectSingerByFirstName.executeByNamedParam(paramMap); //вернуть результат, используя MappingSqlQuery с параметрами
	}

	@Override
	public void update(Singer singer) {
		Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("first_name", singer.getFirstName());
			paramMap.put("last_name", singer.getLastName());
			paramMap.put("birth_date", singer.getBirthDate());
			paramMap.put("id", singer.getId());
		updateSinger.updateByNamedParam(paramMap); //использовать SqlUpdate для обновления
		logger.info("Existing singer updated with id: " + singer.getId());
	}

	@Override
	public void insert(Singer singer) {
		Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("first_name", singer.getFirstName());
			paramMap.put("last_name", singer.getLastName());
			paramMap.put("birth_date", singer.getBirthDate());
		KeyHolder keyHolder = new GeneratedKeyHolder(); //использование keyHolder
		insertSinger.updateByNamedParam(paramMap, keyHolder); //использовать SqlUpdate для вставки
		singer.setId(keyHolder.getKey().longValue()); //полученный после вставки Id установить для singer
		logger.info("New singer inserted with id: " + singer.getId());
	}

	@Override
	public void insertWithAlbum(Singer singer) {
		insertSingerAlbum = new InsertSingerAlbum(dataSource);
		Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("first_name", singer.getFirstName());
			paramMap.put("last_name", singer.getLastName());
			paramMap.put("birth_date", singer.getBirthDate());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		insertSinger.updateByNamedParam(paramMap, keyHolder); //сперва добавляем просто певца
		singer.setId(keyHolder.getKey().longValue());
		logger.info("New singer inserted with id: " + singer.getId());
		List<Album> albums = singer.getAlbums();
		if (albums != null) {
			for (Album album : albums) {
				paramMap = new HashMap<>();
					paramMap.put("singer_id", singer.getId());
					paramMap.put("title", album.getTitle());
					paramMap.put("release_date", album.getReleaseDate());
				insertSingerAlbum.updateByNamedParam(paramMap); //а если у него есть альбомы, то добавляем их
			}
		}
		insertSingerAlbum.flush(); //принудительно сохранить, не дожидаясь наполнения пакета для автоматической вставки
	}

	@Override
	public void delete(Long singerId) {
		Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("id", singerId);
		deleteSinger.updateByNamedParam(paramMap); //использовать SqlUpdate для удаления
		logger.info("Deleting singer with id: " + singerId);
	}

	@Override
	public String findFirstNameById(Long id) {
		List<String> result = storedFunctionFirstNameById.execute(id);
		return result.get(0);
	}

}
