package transaction.config;

import transaction.entities.Album;
import transaction.entities.Singer;
import transaction.repos.SingerRepository;
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
    SingerRepository singerRepository;

	@PostConstruct
	public void initDB(){
		logger.info("Starting database initialization...");

		Singer singer = new Singer("John", "Mayer", new Date(new GregorianCalendar(1977, 9, 16).getTime().getTime()));
			singer.addAlbum(new Album("The Search For Everything", new java.sql.Date(new GregorianCalendar(2017, 0, 20).getTime().getTime())));
			singer.addAlbum(new Album("Battle Studies", new java.sql.Date(new GregorianCalendar(2009, 10, 17).getTime().getTime())));
		Singer singer2 = new Singer("Eric", "Clapton", new Date(new GregorianCalendar(1945, 2, 30).getTime().getTime()));
			singer2.addAlbum(new Album("From The Cradle", new java.sql.Date(new GregorianCalendar(1994, 8, 13).getTime().getTime())));
		Singer singer3 = new Singer("John", "Butler", new Date(new GregorianCalendar(1975, 3, 1).getTime().getTime()));
		singerRepository.save(singer);
		singerRepository.save(singer2);
		singerRepository.save(singer3);

		logger.info("Database initialization finished.");
	}

}
