package remote.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

//конфигурирование модуля Spring MVC с преобразователями сообщений http
@Configuration
@EnableWebMvc //активизирует поддержку аннотаций в модуле Spring MVC, регистрирует систему преобразования типов и форматирования данных в Spring
@ComponentScan(basePackages = {"remote"}) //просмотреть классы контроллеров в указанном пакете
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	ApplicationContext ctx;

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() { //для поддержки формата json
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
			mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper());
		return mappingJackson2HttpMessageConverter;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objMapper = new ObjectMapper();
			objMapper.enable(SerializationFeature.INDENT_OUTPUT);
			objMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			objMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		return objMapper;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) { //объявлятся экземпляры HttpMessageConverter, предназначенные для преобразования сообщениий в поддерживаемых форматах среды передачи
		converters.add(mappingJackson2HttpMessageConverter());
		converters.add(singerMessageConverter());
	}

	@Bean MarshallingHttpMessageConverter singerMessageConverter() { //для поддержки формата xml
		List<MediaType> mediaTypes = new ArrayList<>();
			mediaTypes.add(new MediaType("application", "xml"));
		MarshallingHttpMessageConverter mc = new MarshallingHttpMessageConverter();
			mc.setMarshaller(castorMarshaller());
			mc.setUnmarshaller(castorMarshaller());
			mc.setSupportedMediaTypes(mediaTypes);
		return mc;
	}

	@Bean
    CastorMarshaller castorMarshaller() {
		CastorMarshaller castorMarshaller = new CastorMarshaller();
			castorMarshaller.setMappingLocation(ctx.getResource("classpath:spring/oxm-mapping.xml"));
		return castorMarshaller;
	}
}