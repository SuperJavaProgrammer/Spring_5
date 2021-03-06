package remote.securerest;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
    Credentials credentials(){ //только для Secure
		return new UsernamePasswordCredentials("prospring5", "prospring5");
	}

	@Bean //только для Secure
    CredentialsProvider provider() {
		BasicCredentialsProvider provider = new BasicCredentialsProvider();
			provider.setCredentials(AuthScope.ANY, credentials());
		return provider;
	}

	@Bean //только для Secure
	public HttpComponentsClientHttpRequestFactory factory() {
		CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(provider()).build();
		return new HttpComponentsClientHttpRequestFactory(client);
	}

	@Bean
	public RestTemplate restTemplate() {
		List<HttpMessageConverter<?>> mcvs = new ArrayList<>();
			mcvs.add(singerMessageConverter());
		RestTemplate restTemplate = new RestTemplate();
			restTemplate.setRequestFactory(factory());
			restTemplate.setMessageConverters(mcvs);
		return restTemplate;
	}

	@Bean MarshallingHttpMessageConverter singerMessageConverter() {
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
