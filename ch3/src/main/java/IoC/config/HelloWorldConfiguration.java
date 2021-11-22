package IoC.config;

import IoC.other.Singer;
import IoC.other.SpelConst;
import IoC.provider.HelloWorldMessageProvider;
import IoC.provider.MessageProvider;
import IoC.renderer.MessageRenderer;
import IoC.renderer.StandardOutMessageRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration //это конфигурационный файл Java
//@Import(HelloWorldConfigurationConstructor.class) //импортировать другой настроечный файл java //ch4
//@ImportResource(locations = {"classpath:app-context.xml"}) //импортировать настройки xml
//@ComponentScan(basePackages = {"IoC"}) //сканировать пакет
public class HelloWorldConfiguration {

	@Bean //настроечные бины, настройка для Map
	public Map<String, Object> map() {
		Map<String, Object> map = new HashMap<>();
			map.put("MapKey", "MapValue");
			map.put("MapKey2", "MapValue2");
		return map;
	}

	@Bean
	public Properties prop() {
		Properties prop = new Properties();
			prop.put("PropertiesKey", "PropertiesValue");
			prop.put("PropertiesKey2", "PropertiesValue2");
		return prop;
	}

	@Bean
	public Set set() {
		Set set = new HashSet();
			set.add("Have");
			set.add("Fun");
		return set;
	}

	@Bean
	public List list() {
		List list = new ArrayList();
			list.add("Good");
			list.add("Job");
		return list;
	}

	@Bean //настройка для класса SpelConst
	public SpelConst spelConst() {
		return new SpelConst();
	}

	@Bean //имя бина, по умолчанию = имени метода, т.е. singer
//	@Bean(name = "singer!") //установить свое имя
//	@Bean(name = {"singer", "sng", "sing"}) //установить несколько имен, первое - id, потом псевдонимы
	public Singer singer() {
		Singer singer = new Singer();
			singer.setAge(99);
			singer.setHeigh(500);
			System.out.println("Singer=" + singer);
		return singer;
	}

	@Bean
	public MessageProvider helloWorldMessageProvider() {
		return new HelloWorldMessageProvider();
	}

	@Bean //настройка для сложного класса MessageRenderer, который в себе содержит другой бин
	public MessageRenderer standardOutMessageRenderer(){
		MessageRenderer renderer = new StandardOutMessageRenderer();
			renderer.setMessageProvider(helloWorldMessageProvider());
		return renderer;
	}

}
