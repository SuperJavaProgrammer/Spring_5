package IoC.config;

import IoC.provider.ConfigurableMessageProvider;
import IoC.provider.MessageProvider;
import IoC.renderer.MessageRenderer;
import IoC.renderer.StandardOutMessageRendererConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "classpath:message.properties") //загрузить файл настроек
public class HelloWorldConfigurationConstructor {

	@Autowired //автосвязывание для конфигурации среды
	Environment env;

	@Bean
	public MessageProvider configurableMessageProvider() {
		return new ConfigurableMessageProvider(env.getProperty("message"), 200); //взять даные из файла по значению message
	}

	@Bean(name = "constructor") //задать имя бину, а не использовать по умолчанию standardOutMessageRendererConstructor
	public MessageRenderer standardOutMessageRendererConstructor(){
		MessageRenderer rendererConstructor = new StandardOutMessageRendererConstructor();
			rendererConstructor.setMessageProvider(configurableMessageProvider());
		return rendererConstructor;
	}

}
