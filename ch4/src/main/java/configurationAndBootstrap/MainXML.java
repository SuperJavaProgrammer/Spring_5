package configurationAndBootstrap;

import configurationAndBootstrap.events.Publisher;
import configurationAndBootstrap.messageDigest.MessageDigestFactoryBean;
import configurationAndBootstrap.messageDigest.MessageDigester;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.InputStream;
import java.security.MessageDigest;

public class MainXML {

    public static void main(String[] args) throws Exception {
        System.out.println("Контекст из xml");
        GenericXmlApplicationContext xml = new GenericXmlApplicationContext();
        xml.load("classpath:app-context.xml");
        xml.refresh();

        System.out.println("Получение singerOne с методом до и после");
        Singer bean = (Singer) xml.getBean("singerOne");
        System.out.println(bean); //Name: John Mayer Age: 39

        System.out.println("Проверка работы BeanNameAware для получения имени бина");
        bean.getBeanName(); //Имя бина = singerOne

        System.out.println("Получение singerTwo");
        bean = (Singer) xml.getBean("singerTwo");
        System.out.println(bean); //Name: Eric Clapton Age: 72
        bean.getBeanName(); //Имя бина = singerTwo

        System.out.println("Проверка работы ApplicationContextAware");
        bean.getAllSingers(); //Key=singerOne, Value=Name: John Mayer Age: 39. Key=singerTwo, Value=Name: Eric Clapton Age: 72.

        System.out.println("\nРабота с объектами, которые получаются через фабричные методы");
        MessageDigester digester = (MessageDigester) xml.getBean("digester");
        digester.digest("Hello World!"); //Using digest1: alogrithm: SHA1 [B@6973b51b Using digest2: alogrithm: MD5 [B@1ab3a8c8

        System.out.println("Работа с MessageDigester");
        digester = (MessageDigester) xml.getBean("digesterFactory");
        digester.digest("Hello World!"); //[B@61001b64 [B@4310d43

        System.out.println("Доступ к фабрике напрямую, лучше этого избегать");
        xml.getBean("digest1", MessageDigest.class);
        MessageDigestFactoryBean factoryBean = (MessageDigestFactoryBean) xml.getBean("&digest1"); //&digest1 - так получить напрямую
        MessageDigest shaDigest = factoryBean.getObject(); //throws
        System.out.println(shaDigest.digest("Hello world!".getBytes())); //[B@7e7be63f
        xml.getBean("digest2", MessageDigest.class);
        factoryBean = (MessageDigestFactoryBean) xml.getBean("&digest2");
        MessageDigest md5 = factoryBean.getObject();
        System.out.println(md5.digest("Hello world!".getBytes())); //[B@21282ed8

        System.out.println("Работа с событиями");
        Publisher pub = (Publisher) xml.getBean("publisher");
        pub.publish("I send an SOS to the world... "); //Received: I send an SOS to the world...
        pub.publish("... I hope that someone gets my..."); //Received: ... I hope that someone gets my...
        pub.publish("... Message in a bottle"); //Received: ... Message in a bottle

        System.out.println("Работа с ресурсами");
        File file = File.createTempFile("test", "txt");
        file.deleteOnExit();
        Resource res1 = xml.getResource("file://" + file.getPath());
        displayInfo(res1);
        Resource res2 = xml.getResource("classpath:test.txt");
        displayInfo(res2);
        Resource res3 = xml.getResource("http://www.google.com");
        displayInfo(res3);
        InputStream is = res3.getInputStream();
//        xml.registerShutdownHook(); //поток, который исполняется перед завершением, чтобы не вызывать close()
        xml.close();

        System.out.println("END");
    }

    private static void displayInfo(Resource res) {
        try {
            System.out.println(res.getClass());
            System.out.println(res.getURL().getContent());
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //еще есть:
    //редактор свойств компонентов
    //интернационализация
    //jms, rabbitMq
    //абстракция через интерфейсы environment
    //propertysource - доступ к свойствам среды, переменные окружения, свойства приложения
    //конфигурирование с помощью jsr-330 @Inject @Named("messageProvider") @Singleton, но Spring лучше и эффективнее
    //конфигурирование средствами groovy
}
