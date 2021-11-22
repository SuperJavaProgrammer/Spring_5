package IoC;

import IoC.config.HelloWorldConfiguration;
import IoC.config.HelloWorldConfigurationConstructor;
import IoC.provider.HelloWorldMessageProvider;
import IoC.renderer.MessageRenderer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainJava {
    public static void main(String[] args) {

        System.out.println("Start");
        var java = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class); //для конфигурирования контекста их классов Java

        System.out.println("Данные по певцу:");
        var hwmp = java.getBean("helloWorldMessageProvider", HelloWorldMessageProvider.class);
        System.out.println(hwmp.getSinger()); //Singer{name='Bill', age=40, heigh=3.3, russian=true, salary=90000}
        hwmp.getSinger().display();
//                Map:
//        Key=map, Value={MapKey=MapValue, MapKey2=MapValue2},
//                Properties:
//        Key=PropertiesKey, Value=PropertiesValue, Key=PropertiesKey2, Value=PropertiesValue2,
//                Set:
//        value=Have, value=Fun,
//                List:
//        value=Good, value=Job,
        System.out.println("\nHelloWorldMessageProvider:");
        var renderer = java.getBean("standardOutMessageRenderer", MessageRenderer.class);
        renderer.render(); //Hello World!

        System.out.println("ConfigurableMessageProvider:");
        var javaConstructor = new AnnotationConfigApplicationContext(HelloWorldConfigurationConstructor.class);
        var rendererConstructor = javaConstructor.getBean("constructor", MessageRenderer.class);
        rendererConstructor.render(); //JavaConstructorFromFile

        System.out.println("Finish");
    }
}
