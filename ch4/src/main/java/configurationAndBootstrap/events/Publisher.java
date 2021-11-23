package configurationAndBootstrap.events;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Publisher implements ApplicationContextAware { //отправитель событий, реализует ApplicationContextAware для получения контекста и работы с ним
    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException { //получение контекста
        this.ctx = applicationContext;
    }

    public void publish(String message) { //отправка события
        ctx.publishEvent(new MessageEvent(this, message));
    }

}
