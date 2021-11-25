package hibernate;

import hibernate.config.AppConfig;
import hibernate.dao.SingerDao;
import hibernate.entities.Album;
import hibernate.entities.Singer;
import hibernate.tablesFromEntitiesConfig.AdvancedConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Загрузка контекста: 1) создание таблиц БД через скрипты и наполнение их данными 2) создание таблиц БД из классов сущностей");
//        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class); //для работы активировать AppConfig, а AdvancedConfig и DBInitializer деактивировать
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(AdvancedConfig.class); //для работы активировать AdvancedConfig и DBInitializer, а AppConfig деактивировать
        SingerDao singerDao = ctx.getBean(SingerDao.class);

        System.out.println("\t\tСписок всех певцов:");
        List<Singer> singers = singerDao.findAll();
        singers.forEach(System.out::println);
//        Singer - Id: 1, First name: John, Last name: Mayer, Birthday: 1977-10-16
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01

        System.out.println("\t\tСписок всех певцов с указанием альбомов:");
        singers = singerDao.findAllWithAlbum();
        listSingersWithAlbum(singers);
//        Singer - Id: 1, First name: John, Last name: Mayer, Birthday: 1977-10-16
//              Album - Id: 1, Singer id: 1, Title: The Search For Everything, Release Date: 2017-01-20
//              Album - Id: 2, Singer id: 1, Title: Battle Studies, Release Date: 2009-11-17
//              Instrument :Guitar
//              Instrument :Piano
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//              Album - Id: 3, Singer id: 2, Title: From The Cradle , Release Date: 1994-09-13
//              Instrument :Guitar

        System.out.println("\t\tПолучить певца по айди:");
        Singer singerFindById = singerDao.findById(1L);
        System.out.println(singerFindById);
//        Singer - Id: 1, First name: John, Last name: Mayer, Birthday: 1977-10-16

        System.out.println("\t\tДобавить нового певца с альбомами:");
        Singer singerSave = new Singer("BB", "King", new Date(new GregorianCalendar(1940, 8, 16).getTime().getTime()));
            singerSave.addAlbum(new Album("My Kind of Blues", new java.sql.Date(new GregorianCalendar(1961, 7, 18).getTime().getTime())));
            singerSave.addAlbum(new Album("A Heart Full of Blues", new java.sql.Date(new GregorianCalendar(1962, 3, 20).getTime().getTime())));
        singerDao.save(singerSave);
        singers = singerDao.findAllWithAlbum();
        listSingersWithAlbum(singers);
//        Singer - Id: 4, First name: BB, Last name: King, Birthday: 1940-09-16
//              Album - Id: 5, Singer id: 4, Title: A Heart Full of Blues, Release Date: 1962-04-20
//              Album - Id: 4, Singer id: 4, Title: My Kind of Blues, Release Date: 1961-08-18
//        Singer - Id: 1, First name: John, Last name: Mayer, Birthday: 1977-10-16
//              Album - Id: 2, Singer id: 1, Title: Battle Studies, Release Date: 2009-11-17
//              Album - Id: 1, Singer id: 1, Title: The Search For Everything, Release Date: 2017-01-20
//              Instrument :Guitar
//              Instrument :Piano
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//              Album - Id: 3, Singer id: 2, Title: From The Cradle , Release Date: 1994-09-13
//              Instrument :Guitar

        System.out.println("\t\tРедактирование певца:");
        Singer singer = singerDao.findById(1L);
        Album album = singer.getAlbums().stream().filter(a -> a.getTitle().equals("Battle Studies")).findFirst().get();
        singer.setFirstName("John Clayton");
        singer.removeAlbum(album);
        singerDao.save(singer);
        singers = singerDao.findAllWithAlbum();
        listSingersWithAlbum(singers);
//        Singer - Id: 4, First name: BB, Last name: King, Birthday: 1940-09-16
//              Album - Id: 4, Singer id: 4, Title: My Kind of Blues, Release Date: 1961-08-18
//              Album - Id: 5, Singer id: 4, Title: A Heart Full of Blues, Release Date: 1962-04-20
//        Singer - Id: 1, First name: John Clayton, Last name: Mayer, Birthday: 1977-10-16
//              Album - Id: 1, Singer id: 1, Title: The Search For Everything, Release Date: 2017-01-20
//              Instrument :Piano
//              Instrument :Guitar
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//        Album - Id: 3, Singer id: 2, Title: From The Cradle , Release Date: 1994-09-13
//              Instrument :Guitar
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01

        System.out.println("\t\tУдаление певца:");
        singer = singerDao.findById(1L);
        singerDao.delete(singer);
        singers = singerDao.findAllWithAlbum();
        listSingersWithAlbum(singers);
//        Singer - Id: 4, First name: BB, Last name: King, Birthday: 1940-09-16
//              Album - Id: 5, Singer id: 4, Title: A Heart Full of Blues, Release Date: 1962-04-20
//              Album - Id: 4, Singer id: 4, Title: My Kind of Blues, Release Date: 1961-08-18
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//              Album - Id: 3, Singer id: 2, Title: From The Cradle , Release Date: 1994-09-13
//              Instrument :Guitar
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01

        ctx.close();
    }

    private static void listSingersWithAlbum(List<Singer> singers) {
        singers.forEach(singer -> {
            System.out.println(singer);
            if (singer.getAlbums() != null)
                singer.getAlbums().forEach(album -> System.out.println("   " + album));
            if (singer.getInstruments() != null) {
                singer.getInstruments().forEach(i -> System.out.println("   " + i));
            }
        });
    }

}
