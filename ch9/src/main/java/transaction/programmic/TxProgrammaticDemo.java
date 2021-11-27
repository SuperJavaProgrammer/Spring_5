package transaction.programmic;

import transaction.config.DataJpaConfig;
import transaction.services.SingerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class TxProgrammaticDemo {

	//для проверки закомментировать @Service("singerService") в package transaction.services; SingerServiceImpl
	//раскомментировать в package transaction.programmic;
	public static void main(String... args) {
		GenericApplicationContext ctx = new AnnotationConfigApplicationContext(ServicesConfig.class, DataJpaConfig.class);
		SingerService singerService = ctx.getBean(SingerService.class);
		System.out.println("Singer count: " + singerService.countAll());
		ctx.close();
	}
}
