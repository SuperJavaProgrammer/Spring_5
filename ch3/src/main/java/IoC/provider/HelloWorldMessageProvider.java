package IoC.provider;

import IoC.other.Singer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component("helloWorldMessageProvider")
@DependsOn("singer") //зависит от singer, получить зависимость singer первой
public class HelloWorldMessageProvider implements MessageProvider {

    @Autowired //внедрение через поле, такая зависимость должна быть для внедрения
    private Singer singer;

    public HelloWorldMessageProvider() {
        System.out.println("HelloWorldMessageProvider: constructor called");
    }

    @Override
    public String getMessage() { //реализация интерфейса, возвращает константу
        return "Hello World!";
    }

    public void singSong() {
        System.out.println("Song is: " + singer.getSong());
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

//    @Lookup("singer") //{return null;} //внедрение через метод поиска, выполняется дольше по времени todo
    public Singer getSinger() {
        return singer;
    }
}
