package configurationAndBootstrap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

public class Singer implements InitializingBean, DisposableBean, BeanNameAware, ApplicationContextAware { //для установки методов До, После, получения имени бина, понимания общего Spring контекста
    private static final String DEFAULT_NAME = "Eric Clapton";
    private String name;
    private int age = Integer.MIN_VALUE;
    private String beanName;
    private ApplicationContext applicationContext;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private void init() { //выполняется третьим
        System.out.println("Initializing bean");
        if (name == null) {
            System.out.println("Using default name");
            name = DEFAULT_NAME;
        }
        if (age == Integer.MIN_VALUE)
            throw new IllegalArgumentException("You must set the age property of any beans of type " + Singer.class);
    }

    private void close() { //выполняется третьим
        System.out.println("Closing actions...");
        System.out.println("Done!");
    }

    public String toString() {
        return "Name: " + name + " Age: " + age;
    }

    @PostConstruct //выполняется первым
    private void initPostConstruct() {
        System.out.println("initPostConstruct");
        init();
    }

    @PreDestroy //выполняется первым
    private void closePreDestroy() {
        System.out.println("closePreDestroy");
        init();
    }

    @Override //выполняется вторым
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet");
        init();
    }

    @Override //выполняется вторым
    public void destroy() {
        System.out.println("destroy");
        close();
    }

    @Override //для получения имени бина
    public void setBeanName(String s) {
        beanName = s;
    }

    void getBeanName() {
        System.out.println("Имя бина = " + beanName);
    }

    @Override //получение Spring контекста
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }

    void getAllSingers() {
        Map<String, Singer> allSingers = applicationContext.getBeansOfType(Singer.class); //использование полученного ранее контекста
        allSingers.forEach((key, value) -> System.out.print("Key=" + key + ", Value=" + value + ". "));
    }

}
