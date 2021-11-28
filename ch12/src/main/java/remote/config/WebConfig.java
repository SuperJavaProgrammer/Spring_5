package remote.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer { //WebMvcConfigurer - определяются методы обратного вызова для настройки модуля Spring MVC, активизируемого с помощью @EnableSpringMVC
}