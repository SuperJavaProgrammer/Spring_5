package configurationAndBootstrap;

import configurationAndBootstrap.config.SingerConfig;
import configurationAndBootstrap.messageDigest.MessageDigester;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class MainJava {

    public static void main(String[] args) {
        System.out.println("Контекст из Java");
        GenericApplicationContext java = new AnnotationConfigApplicationContext(SingerConfig.class);

        System.out.println("Получение singerOne с методом до и после");
        Singer bean = (Singer) java.getBean("singerOne");
        System.out.println(bean); //Name: John Mayer Age: 39

        System.out.println("Получение singerTwo");
        bean = (Singer) java.getBean("singerTwo");
        System.out.println(bean); //Name: Eric Clapton Age: 72

        System.out.println("Получение singerThree");
        try {
            bean = (Singer) java.getBean("singerThree");
            System.out.println(bean);
        } catch (Exception e) {
            System.out.println("something went wrong...");
            e.printStackTrace(); //Error creating bean with name 'singerThree': Invocation of init method failed; nested exception is java.lang.IllegalArgumentException: You must set the age property of any beans of type class configurationAndBootstrap.Singer
        }

        System.out.println("Работа с объектами, которые получаются через фабричные методы");
        MessageDigester digester = (MessageDigester) java.getBean("digester");
        digester.digest("Hello World!"); //[B@359df09a [B@43df23d3

        System.out.println("END");
    }

}
