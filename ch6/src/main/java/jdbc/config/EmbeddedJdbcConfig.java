package jdbc.config;

import jdbc.dao.JdbcSingerDao;
import jdbc.dao.SingerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"jdbc"})
public class EmbeddedJdbcConfig {

	private static Logger logger = LoggerFactory.getLogger(EmbeddedJdbcConfig.class);

	@Bean
	public DataSource dataSource() { //настройка источника данных
		try {
			EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder(); //внутренняя БД
			return dbBuilder.setType(EmbeddedDatabaseType.H2) //установить тип
					.addScripts("classpath:h2/schema.sql", "classpath:h2/test-data.sql") //добавить скрипты
					.build();
		} catch (Exception e) {
			logger.error("Embedded DataSource bean cannot be created!", e);
			return null;
		}
	}

	@Bean
	public JdbcTemplate jdbcTemplate(){
		return new JdbcTemplate(dataSource());
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(){ //для работы с именованными параметрами
		return new NamedParameterJdbcTemplate(dataSource());
	}

	@Bean
	public SingerDao singerDao() {
		JdbcSingerDao dao = new JdbcSingerDao();
			dao.setJdbcTemplate(jdbcTemplate());
			dao.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate());
		return dao;
	}

}
