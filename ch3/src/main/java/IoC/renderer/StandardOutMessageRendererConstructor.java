package IoC.renderer;

import IoC.provider.MessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("standardOutMessageRendererConstructor")
public class StandardOutMessageRendererConstructor implements MessageRenderer {

	private MessageProvider messageProvider;

	public StandardOutMessageRendererConstructor(){
		System.out.println("StandardOutMessageRenderer: constructor called");
	}

	@Override
	public void render() {
		if (messageProvider == null)
			throw new RuntimeException("You must set the property messageProvider of class:" + StandardOutMessageRendererConstructor.class.getName());
		System.out.println(messageProvider.getMessage());
	}

	@Override
	@Autowired
	@Qualifier("configurableMessageProvider")
	public void setMessageProvider(MessageProvider provider) {
		System.out.println("StandardOutMessageRenderer: setting the provider");
		this.messageProvider = provider;
	}

	@Override
	public MessageProvider getMessageProvider() {
		return this.messageProvider;
	}
}
