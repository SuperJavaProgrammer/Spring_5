package webmvc.init;

import webmvc.config.DataServiceConfig;
//import webmvc.config.SecurityConfig;
import webmvc.config.WebConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer { //чтобы воспользоватья конфигурацией на основе кода, нужно разработать класс, реализующий интерфейс WebApplicationInitializer

	@Override
	protected Class<?>[] getRootConfigClasses() { //корневой контекст приложения будет создан с помощью конфигурационных классов, возвращаемых данным методом
		return new Class<?>[]{
				/*SecurityConfig.class,*/ DataServiceConfig.class
		};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() { //контекст веб-приложения будет создан с помощью конфигурационных классов, возвращаемых данным методом
		return new Class<?>[]{
				WebConfig.class
		};
	}

	@Override
	protected String[] getServletMappings() { //сопоставления (контекста) с помощью сервлета диспетчера указывается в массиве символьных строк, возвращаемых этим методом
		return new String[]{"/"};
	}

	@Override
	protected Filter[] getServletFilters() { //возвращаем массив реализаций фильтов, предназначенных для применения к каждому запросу
		CharacterEncodingFilter cef = new CharacterEncodingFilter(); //служит для обозначения кодировки символов в запросе
			cef.setEncoding("UTF-8");
			cef.setForceEncoding(true);
		return new Filter[]{new HiddenHttpMethodFilter(), cef}; //поддерживает другие методы доступа по протоколу http, кроме get и post
	}

//	// <=> <multipart-config>
//	@Override
//	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
//		registration.setMultipartConfig(getMultipartConfigElement());
//	}
//
//	private MultipartConfigElement getMultipartConfigElement() {
//		return  new MultipartConfigElement(
//				null, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
//	}
//
//	private static final long MAX_FILE_SIZE = 5000000;
//	// Beyond that size spring will throw exception.
//	private static final long MAX_REQUEST_SIZE = 5000000;
//
//	// Size threshold after which files will be written to disk
//	private static final int FILE_SIZE_THRESHOLD = 0;
}