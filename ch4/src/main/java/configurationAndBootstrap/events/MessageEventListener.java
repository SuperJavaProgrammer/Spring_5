package configurationAndBootstrap.events;

import org.springframework.context.ApplicationListener;

public class MessageEventListener implements ApplicationListener<MessageEvent> { //слушатель событий
    @Override
    public void onApplicationEvent(MessageEvent event) { //метод при возникновении события
        MessageEvent msgEvt = event;
        System.out.println("Received: " + msgEvt.getMessage());
    }
}
