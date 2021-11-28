package remote.rest;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestClientConfig {

	@Autowired
	ApplicationContext ctx;

	@Bean
	public HttpComponentsClientHttpRequestFactory httpRequestFactory() {
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		HttpClient httpClient = HttpClientBuilder.create().build();
		httpRequestFactory.setHttpClient(httpClient);
		return httpRequestFactory;
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
		List<HttpMessageConverter<?>> mcvs = new ArrayList<>();
		mcvs.add(singerMessageConverter());
		restTemplate.setMessageConverters(mcvs);
		return restTemplate;
	}

	@Bean MarshallingHttpMessageConverter singerMessageConverter() {
		List<MediaType> mediaTypes = new ArrayList<>(); //внедряются поддерживаемые типы среды
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
			castorMarshaller.setMappingLocation(ctx.getResource( "classpath:spring/oxm-mapping.xml"));
		return castorMarshaller;
	}
}
