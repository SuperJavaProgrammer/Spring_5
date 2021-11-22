package IoC;

import IoC.other.Singer;
import IoC.other.TargetAutowire;
import IoC.provider.HelloWorldMessageProvider;
import IoC.renderer.MessageRenderer;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Arrays;
import java.util.Map;

public class MainXml {
    public static void main(String[] args) {
        /**
         Инверсия управления: поиск(компонент должен получать ссылку на зависимость) и внедрение зависимостей(они внедряются в компонент контейнером инверсии управления)
         Внедрение через конструктор, через метод установки, через поле.
         Конфигурирование приложений Spring: xml, аннотации, на Java
         Автосвязывание: byName, byType, constructor, default (byType/constructor)
         */

        System.out.println("Start");
        GenericXmlApplicationContext xml = new GenericXmlApplicationContext(); //контекст для xml файлов
        xml.load("classpath:app-context.xml"); //загрузить настройки контекста из xml файла
        xml.refresh();

        System.out.println("Получаем отображение со всеми компонентами String:");
        Map<String, String> beans = xml.getBeansOfType(String.class);
        beans.forEach((key, value) -> System.out.print("Key=" + key + ", Value=" + value + ". "));
        //Key=string1, Value=ValueForString1. Key=string2, Value=ValueForString2. Key=string3, Value=. Key=java.lang.String#0, Value=. Key=java.lang.String#1, Value=.

        System.out.println("\nПолучаем разные строки из контекста по id и name:");
        String string1_0 = (String) xml.getBean("string1"); //получаем первую строку по айди
        String string1_1 = (String) xml.getBean("str1"); //получаем эту же строку по имени
        String string2 = (String) xml.getBean("st2"); //получаем вторую строку по alias
        System.out.println("string1_0 == string1_1 " + (string1_0 == string1_1)); //true
        System.out.println("string1_0 == string2 " + (string1_0 == string2)); //false

        System.out.println("Получаем все псевдонимы:");
        String[] aliases = xml.getAliases("s1");
        for (String alias : aliases) //Arrays.stream(aliases).forEach((alias) -> System.out.print(alias + ", "));
            System.out.print(alias + ", "); //string1, str, st1, strng1, str1,

        System.out.println("Проверка автосвязывания:");
        TargetAutowire t;
        System.out.println("Autowire byName:");
        t = (TargetAutowire) xml.getBean("targetByName"); //Property fooOne set
        System.out.println("Autowire byType:"); //Property bar set Property fooOne set Property fooTwo set
        t = (TargetAutowire) xml.getBean("targetByType");
        System.out.println("Autowire constructor:");
        t = (TargetAutowire) xml.getBean("targetConstructor"); //Target(Foo, Bar) called

        System.out.println("Получение разных реализаций интерфейсов:");
        System.out.println("HelloWorldMessageProvider:");
        MessageRenderer renderer = xml.getBean("standardOutMessageRenderer", MessageRenderer.class);
        renderer.render(); //Hello World!
        System.out.println("ConfigurableMessageProvider Constructor1:");
        MessageRenderer rendererConstructor = xml.getBean("rendererConstructor1", MessageRenderer.class);
        rendererConstructor.render(); //providerConstructor1
        System.out.println("ConfigurableMessageProvider Constructor2:");
        MessageRenderer rendererConstructor2 = xml.getBean("rendererConstructor2", MessageRenderer.class);
        rendererConstructor2.render(); //providerConstructor2

        System.out.println("Работа с SpEL:");
        HelloWorldMessageProvider hwmp = xml.getBean("helloWorldMessageProvider", HelloWorldMessageProvider.class);
        System.out.println(hwmp.getSinger()); //Singer{name='Super Bill', age=48, heigh=1.75, russian=false, salary=80000}
        hwmp.getSinger().display();
//                Map:
//        Key=key1xml, Value=Value1xml, Key=key2xml, Value=IoC.provider.ConfigurableMessageProvider@2931522b,
//                Properties:
//        Key=firstName, Value=Hello, Key=secondName, Value=World,
//                Set:
//        value=I believe, value=IoC.provider.ConfigurableMessageProvider@2931522b,
//                List:
//        value=Everything is possible, value=IoC.provider.ConfigurableMessageProvider@2931522b,

        System.out.println("\nДочерний бин наследует свойства родительского:");
        Singer singer = xml.getBean("singer", Singer.class);
        System.out.println("Родительский: " + singer); //Singer{name='Super Bill', age=48, heigh=1.75, russian=false, salary=80000}
        Singer singerChild = xml.getBean("singerChild", Singer.class);
        System.out.println("Дочерний: " + singerChild); //Singer{name='Child', age=0, heigh=1.75, russian=false, salary=80000}

        System.out.println("Finish");
        xml.close(); //нужно для GenericXmlApplicationContext
    }
}
