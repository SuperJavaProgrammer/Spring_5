package IoC;

import IoC.provider.HelloWorldMessageProvider;
import IoC.renderer.MessageRenderer;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainAnn {
    public static void main(String[] args) {

        System.out.println("Start");
        var ann = new GenericXmlApplicationContext();
        ann.load("classpath:app-context-annotation.xml");
//        ann.setParent(xml); //установить родительский контекст
        ann.refresh();

        System.out.println("Получение разных реализаций интерфейсов:");
        System.out.println("HelloWorldMessageProvider:");
        var renderer = ann.getBean("standardOutMessageRenderer", MessageRenderer.class); //получить бин standardOutMessageRenderer типа MessageRenderer из контекста
        renderer.render(); //Hello World!
        System.out.println("ConfigurableMessageProvider:");
        var rendererConstructor = ann.getBean("standardOutMessageRendererConstructor", MessageRenderer.class);
        rendererConstructor.render(); //message annotation

        System.out.println("Автосвязывание singer через поле:");
        var hwmp = ann.getBean("helloWorldMessageProvider", HelloWorldMessageProvider.class);
        hwmp.singSong(); //Song is: Viva la vida loca

        System.out.println("Применение SpEL:");
        System.out.println(hwmp.getSinger()); //Singer{name='Bill', age=40, heigh=3.3, russian=true, salary=90000}

        System.out.println("Внедрение коллекций:");
        hwmp.getSinger().display();
//                Map:
//        Key=map, Value={someBean=message annotation, someValue=Jane},
//                Properties:
//        Key=firstName, Value=Bill, Key=secondName, Value=Gates,
//                Set:
//        value=Friend, value=500,
//                List:
//        value=Trance, value=I know I won't be injected :(,

        System.out.println("Finish");
        ann.close();
    }
}
