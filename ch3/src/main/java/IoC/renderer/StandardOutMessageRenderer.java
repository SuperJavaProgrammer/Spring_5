package IoC.renderer;

import IoC.provider.MessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

@Service("standardOutMessageRenderer") //если простой объект, то Component, если сложный, то Service, но это не строгое правило
public class StandardOutMessageRenderer implements MessageRenderer {

	private MessageProvider messageProvider;

	public StandardOutMessageRenderer(){
		System.out.println("StandardOutMessageRenderer: constructor called");
	}

	@Override
	public void render() {
		if (messageProvider == null)
			throw new RuntimeException("You must set the property messageProvider of class:" + StandardOutMessageRenderer.class.getName());
		System.out.println(messageProvider.getMessage());
	}

	@Override
	@Autowired //внедрение зависимостей через метод установки
	@Qualifier("helloWorldMessageProvider") //автосвязывание по имени, если есть несколько кандидатов этого типа
	//@Inject и @Resource(name = "name") - аналоги не из Spring
	public void setMessageProvider(MessageProvider provider) {
		System.out.println("StandardOutMessageRenderer: setting the provider");
		this.messageProvider = provider;
	}

	@Override
	public MessageProvider getMessageProvider() {
		return this.messageProvider;
	}
}
