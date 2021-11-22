package IoC.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary //Если есть несколько кандидатов на внедрение, то использовать этот приоритетный
class Foo {
    public Foo(){ }
}

@Component
class Bar {
    public Bar(){ }
}

@Lazy //только при запросе создавать, а не при инициализации контекста
public class TargetAutowire {

    private Foo fooOne;
    private Foo fooTwo;
    private Bar bar;

    public TargetAutowire() { }

    public TargetAutowire(Foo foo) {
        System.out.println("Target(Foo) called");
    }

    public TargetAutowire(Foo foo, Bar bar) {
        System.out.println("Target(Foo, Bar) called");
    }

    @Autowired //автосвязывание
    public void setFooOne(Foo fooOne) {
        this.fooOne = fooOne;
        System.out.println("Property fooOne set");
    }

    @Autowired
    public void setFooTwo(Foo foo) {
        this.fooTwo = foo;
        System.out.println("Property fooTwo set");
    }

    @Autowired
    public void setBar(Bar bar) {
        this.bar = bar;
        System.out.println("Property bar set");
    }
}
