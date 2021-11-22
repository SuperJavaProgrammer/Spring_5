package IoC.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("configurableMessageProvider") //имя компонента равно имени класса, с маленькой буквы
public class ConfigurableMessageProvider implements MessageProvider {
    private String message;
    private String message2;

    public ConfigurableMessageProvider() { }

    public ConfigurableMessageProvider(String message2) { //(@Value("message1") String message2) жесткая установка значения через конструктор
        System.out.println("ConfigurableMessageProvider constructor with String message2");
        this.message = message2;
    }

    @Autowired //автосвязывание для конструктора, только для одного конструктора можно применять
    public ConfigurableMessageProvider(String message, Integer message2) {
        System.out.println("ConfigurableMessageProvider constructor with String message, int message2");
        this.message = message;
        this.message2 = message2 + "int";
    }

    public ConfigurableMessageProvider(int message, String message2) {
        System.out.println("ConfigurableMessageProvider constructor with int message, String message2");
        this.message = message + "int";
        this.message2 = message2;
    }

    @Override
    public String getMessage() { //реализация интерфейса, возвращает указанное сообщение
        return message;
    }

}
