package webmvc.services;

import webmvc.entities.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class DBInitializer {

	private Logger logger = LoggerFactory.getLogger(DBInitializer.class);
	@Autowired
	SingerService singerService;

	@PostConstruct
	public void initDB() {
		logger.info("Starting database initialization...");

		singerService.save(new Singer("John", "Mayer", new Date(new GregorianCalendar(1977, 9, 16).getTime().getTime())));
		singerService.save(new Singer("Eric", "Clapton", new Date(new GregorianCalendar(1945, 2, 30).getTime().getTime())));
		singerService.save(new Singer("John", "Butler", new Date(new GregorianCalendar(1975, 3, 1).getTime().getTime())));
		singerService.save(new Singer("B.B.", "King", new Date(new GregorianCalendar(1925, 9, 16).getTime().getTime())));
		singerService.save(new Singer("Jimi", "Hendrix", new Date(new GregorianCalendar(1942, 11, 27).getTime().getTime())));
		singerService.save(new Singer("Jimmy", "Page", new Date(new GregorianCalendar(1944, 1, 9).getTime().getTime())));
		singerService.save(new Singer("Eddie", "Van Halen", new Date(new GregorianCalendar(1955, 1, 26).getTime().getTime())));
		singerService.save(new Singer("Saul (Slash)", "Hudson", new Date(new GregorianCalendar(1965, 7, 23).getTime().getTime())));
		singerService.save(new Singer("Stevie", "Ray Vaughan", new Date(new GregorianCalendar(1954, 10, 3).getTime().getTime())));
		singerService.save(new Singer("David", "Gilmour", new Date(new GregorianCalendar(1946, 3, 6).getTime().getTime())));
		singerService.save(new Singer("Kirk", "Hammett", new Date(new GregorianCalendar(1992, 11, 18).getTime().getTime())));
		singerService.save(new Singer("Angus", "Young", new Date(new GregorianCalendar(1955, 3, 31).getTime().getTime())));
		singerService.save(new Singer("Dimebag", "Darrell", new Date(new GregorianCalendar(1966, 8, 20).getTime().getTime())));
		singerService.save(new Singer("Carlos", "Santana", new Date(new GregorianCalendar(1947, 7, 20).getTime().getTime())));

		logger.info("Database initialization finished.");
	}
}
