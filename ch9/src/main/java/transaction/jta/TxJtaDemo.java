package transaction.jta;

import transaction.entities.Singer;
import transaction.services.SingerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TxJtaDemo {
	private static Logger logger = LoggerFactory.getLogger(TxJtaDemo.class);

	//сперва нужно созадть БД, так не работает
	public static void main(String... args) {
		GenericApplicationContext ctx = new AnnotationConfigApplicationContext(ServicesConfig.class, XAJpaConfig.class);
		SingerService singerService = ctx.getBean(SingerService.class);
		Singer singer = new Singer("John", "Mayer", new Date(new GregorianCalendar(1977, 9, 16).getTime().getTime()));
		singerService.save(singer);
		if (singer.getId() != null) logger.info("--> Singer saved successfully");
		else logger.error("--> Singer was not saved, check the configuration!!");

		// check saving in both databases
		List<Singer> singers = singerService.findAll();
		if (singers.size() != 2) logger.error("--> Something went wrong.");
		else logger.info("--> Singers from both DBs: " + singers);

		ctx.close();
	}
} 
