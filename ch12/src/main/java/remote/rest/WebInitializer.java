package remote.rest;

import remote.config.DataServiceConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//веб-приложения разрабатываются по проектному шаблону Единая точка входа, где все запросы получаются одним контроллером, который затем перенаправляет их соответствующим обработчикам (классам контроллеров). Центральный диспетчер - DispatcherServlet, регистрируемый в AbstractAnnotationConfigDispatcherServletInitializer
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                DataServiceConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() { //предписывается обрабатывать все URL по шаблону / с помощью сервлета rest
        return new String[]{"/"};
    }

}
