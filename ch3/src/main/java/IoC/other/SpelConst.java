package IoC.other;

import org.springframework.stereotype.Component;

@Component //компонент для предоставления другим объектам констанкт
public class SpelConst {
    private String name = "Bill";
    private int age = 50;
    private float heigh = 2.0f;
    private boolean russian = false;
    private long salary = 60000;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public float getHeigh() {
        return heigh;
    }

    public boolean isRussian() {
        return russian;
    }

    public long getSalary() {
        return salary;
    }
}
