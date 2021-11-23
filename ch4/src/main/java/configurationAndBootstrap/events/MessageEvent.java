package configurationAndBootstrap.events;

import org.springframework.context.ApplicationEvent;

public class MessageEvent extends ApplicationEvent { //событие - сообщение
    private String msg;

    public MessageEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMessage() {
        return msg;
    }
}
