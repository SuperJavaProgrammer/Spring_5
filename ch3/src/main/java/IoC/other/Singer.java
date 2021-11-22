package IoC.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Component //Конфигурирование через аннотиции. По умолчанию используется имя "singer" - Класс, но первая буква маленькая. Полностью так @Component("singer")
//@Scope("prototype") //не одиночный объект, singleton одиночный по умолчанию. Запрос - для MVC создаются по каждому http запросу и уничтожаются по окончанию. Сеанс связи - для MVC создаются для каждого http сеанса, в конце уничтожаются. Глобальный сеанс связи - веб-приложения, основанных на портлетах, для MVC. Поток исполнения - новый экземпляр по запросу из нового потока исполнения. Специальная - можно создать, реализовав интерфейс Scope
public class Singer {
    @Value("Bill") //жестко задать имя полю
    private String name;
    @Value("#{spelConst.age - 10}") //применить Spel с преобразованиями
    private int age;
    @Value("#{spelConst.heigh + 1.3}")
    private float heigh;
    @Value("#{!spelConst.russian}")
    private boolean russian;
    @Value("90000")
    private long salary;

    @Autowired //автосвязывание
    @Qualifier("map") //указать имя, чтобы если такой тип уже есть не было неоднозначности
//    @Resource(name = "map") //можно так через другую не Spring аннотацию
    private Map<String, Object> map;
    @Autowired
    @Qualifier("prop")
    private Properties prop;
    @Autowired
    @Qualifier("set")
    private Set set;
    @Autowired
    @Qualifier("list")
    private List list;

    private String song = "Viva la vida loca";

    public String getSong() {
        return song;
    }

    @Override
    public String toString() {
        return "Singer{" +
                "name=" + name +
                ", age=" + age +
                ", heigh=" + heigh +
                ", russian=" + russian +
                ", salary=" + salary +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeigh(float heigh) {
        this.heigh = heigh;
    }

    public void setRussian(boolean russian) {
        this.russian = russian;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public void setList(List list) {
        this.list = list;
    }

    public void display() { //метода для отображения по коллекциям
        System.out.println("Map:");
        map.forEach((key, value) -> System.out.print("Key=" + key + ", Value=" + value + ", "));
        System.out.println("\nProperties:");
        prop.forEach((key, value) -> System.out.print("Key=" + key + ", Value=" + value + ", "));
        System.out.println("\nSet:");
        set.forEach(a -> System.out.print("value=" + a + ", "));
        System.out.println("\nList:");
        list.forEach(a -> System.out.print("value=" + a+ ", "));
    }
}
