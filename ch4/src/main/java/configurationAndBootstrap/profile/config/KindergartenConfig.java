package configurationAndBootstrap.profile.config;

import configurationAndBootstrap.profile.FoodProviderService;
import configurationAndBootstrap.profile.kindergarten.FoodProviderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("kindergarten")
public class KindergartenConfig {
	@Bean
	public FoodProviderService foodProviderService(){ //вернуть еду для детского сада
		return new FoodProviderServiceImpl();
	}
}