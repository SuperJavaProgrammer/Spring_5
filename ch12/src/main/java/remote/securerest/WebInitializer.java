package remote.securerest;

import remote.config.DataServiceConfig;
import remote.rest.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                DataServiceConfig.class, SecurityConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class, SecurityConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() { //предписывается обрабатывать все URL по шаблону / с помощью сервлета rest
        return new String[]{"/"};
    }

}
