package configurationAndBootstrap.profile.config;

import configurationAndBootstrap.profile.FoodProviderService;
import configurationAndBootstrap.profile.highschool.FoodProviderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("highschool") //использование профиля. Если этот профиль активен - настроить его бины
public class HighschoolConfig {
	@Bean
	public FoodProviderService foodProviderService(){ //вернуть еду для школы
		return new FoodProviderServiceImpl();
	}
}
