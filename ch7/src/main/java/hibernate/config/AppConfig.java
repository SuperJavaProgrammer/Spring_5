package hibernate.config;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

//@Configuration
//@ComponentScan(basePackages = "hibernate")
//@EnableTransactionManagement //поддержка требований к установлению границ транзакций
public class AppConfig {

	private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

	@Bean
	public DataSource dataSource() { //источник БД, встроенная
		try {
			EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
			return dbBuilder.setType(EmbeddedDatabaseType.H2)
					.addScripts("classpath:sql/schema.sql", "classpath:sql/test-data.sql").build();
		} catch (Exception e) {
			logger.error("Embedded DataSource bean cannot be created!", e);
			return null;
		}
	}

	private Properties hibernateProperties() {
		Properties hibernateProp = new Properties();
			hibernateProp.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect"); //далект БД
			hibernateProp.put("hibernate.format_sql", true); //следует ли форматировать вывод запросов sql в файл/на консоль
			hibernateProp.put("hibernate.use_sql_comments", true); //формировать комментарии в запросах sql
			hibernateProp.put("hibernate.show_sql", true); //направлять запросы sql в файл/на консоль
			hibernateProp.put("hibernate.max_fetch_depth", 3); //глубина для внешних соединений
			hibernateProp.put("hibernate.jdbc.batch_size", 10); //количество операций обновления, которые должны быть сгруппированы в пакет
			hibernateProp.put("hibernate.jdbc.fetch_size", 50); //количество записей из ResultSet для одной выборки
		return hibernateProp;
	}

	@Bean
	public SessionFactory sessionFactory() throws IOException { //гланые настройки по сессии для Hibernate
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
			sessionFactoryBean.setDataSource(dataSource());
			sessionFactoryBean.setPackagesToScan("hibernate.entities");
			sessionFactoryBean.setHibernateProperties(hibernateProperties());
			sessionFactoryBean.afterPropertiesSet();
		return sessionFactoryBean.getObject();
	}

	@Bean //для транзакционного доступа к данным
	public PlatformTransactionManager transactionManager() throws IOException {
		return new HibernateTransactionManager(sessionFactory());
	}
}
