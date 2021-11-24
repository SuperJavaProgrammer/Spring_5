package jdbc.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;

//@Configuration //для работы с не встроенной БД
//@PropertySource("classpath:db/jdbc2.properties")
//@ComponentScan("jdbc")
public class DbConfig {

	@Value("${driverClassName}") //получить значение из файла свойств
	private String driverClassName;
	@Value("${url}")
	private String url;
	@Value("${username}")
	private String username;
	@Value("${password}")
	private String password;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@SuppressWarnings("unchecked")
	@Lazy
	@Bean
	public DataSource dataSource() { //настроить источник данных
		try {
			SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//			BasicDataSource dataSource = new BasicDataSource(); //или так
			Class<? extends Driver> driver = (Class<? extends Driver>) Class.forName(driverClassName);
				dataSource.setDriverClass(driver);
//				dataSource.setDriverClassName(driverClassName); //или так
				dataSource.setUrl(url);
				dataSource.setUsername(username);
				dataSource.setPassword(password);
			return dataSource;
		} catch (Exception e) {
			return null;
		}
	}

}
