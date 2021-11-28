package remote.entities;

import java.io.Serializable;
import java.util.List;

public class Singers implements Serializable { //для поддержки преобразования данных в формат xml/json
    private List<Singer> singers;

    public Singers() { }

    public Singers(List<Singer> singers) {
        this.singers = singers;
    }

    public List<Singer> getSingers() {
        return singers;
    }

    public void setSingers(List<Singer> singers) {
        this.singers = singers;
    }
}
