package remote.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer { //чтобы программно сконфигурировать контекст типа ServletContext и не предоставлять файл web.xml, в классе импортируетсяконфигурация доступа к данными обработки транзакций, создается контекст корневого приложения

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                DataServiceConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                HttpInvokerConfig.class, WebConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/invoker/*"};
    }

}