package jpa2;

import jpa2.config.JpaConfig;
import jpa2.entities.Album;
import jpa2.entities.Singer;
import jpa2.service.SingerService;
import jpa2.service.SingerServiceImpl;
import jpa2.untype.SingerSummaryService;
import jpa2.untype.SingerSummary;
import jpa2.untype.SingerSummaryUntypeImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(JpaConfig.class);
        SingerService singerService = ctx.getBean(SingerService.class); //почему не находит SingerServiceImpl?

        System.out.println("Поиск всех певцов");
        List<Singer> singers = singerService.findAll();
        singers.forEach(System.out::println);
//        Singer - Id: 1, First name: John, Last name: Mayer, Birthday: 1977-10-16
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01

        System.out.println("Поиск всех певцов с альбомами и инструментами");
        listSingersWithAlbum(singerService.findAllWithAlbum());
//        Singer - Id: 1, First name: John, Last name: Mayer, Birthday: 1977-10-16
//              Album - id: 2, Singer id: 1, Title: Battle Studies, Release Date: 2009-11-17
//              Album - id: 1, Singer id: 1, Title: The Search For Everything, Release Date: 2017-01-20
//                  Instrument: Instrument :Piano
//                  Instrument: Instrument :Guitar
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//              Album - id: 3, Singer id: 2, Title: From The Cradle , Release Date: 1994-09-13
//                  Instrument: Instrument :Guitar

        System.out.println("Поиск певца по Id " + singerService.findById(1L));
//        Singer - Id: 1, First name: John, Last name: Mayer, Birthday: 1977-10-16

        System.out.println("Запрос нетипизированных результатов");
        SingerSummaryUntypeImpl singerSummaryUntype = ctx.getBean(SingerSummaryUntypeImpl.class);
        singerSummaryUntype.displayAllSingerSummary();
//        Номер 1: John, Mayer, The Search For Everything
//        Номер 2: Eric, Clapton, From The Cradle

        System.out.println("Запрос нетипизированных результатов с помощью конструктора");
        SingerSummaryService singerSummaryService = ctx.getBean(SingerSummaryService.class); //SingerSummaryServiceImpl?
        List<SingerSummary> singersUntype = singerSummaryService.findAll();
        singersUntype.forEach(System.out::println);
//        First name: John, Last Name: Mayer, Most Recent Album: The Search For Everything
//        First name: Eric, Last Name: Clapton, Most Recent Album: From The Cradle

        System.out.println("Добавление новой записи");
        Singer singer = new Singer("BB", "King", new Date(new GregorianCalendar(1940, 8, 16).getTime().getTime()));
            singer.addAlbum(new Album("My Kind of Blues", new java.sql.Date(new GregorianCalendar(1961, 7, 18).getTime().getTime())));
            singer.addAlbum(new Album("A Heart Full of Blues", new java.sql.Date(new GregorianCalendar(1962, 3, 20).getTime().getTime())));
        singerService.save(singer);
        listSingersWithAlbum(singerService.findAllWithAlbum());
//        Singer - Id: 4, First name: BB, Last name: King, Birthday: 1940-09-16
//              Album - id: 5, Singer id: 4, Title: A Heart Full of Blues, Release Date: 1962-04-20
//              Album - id: 4, Singer id: 4, Title: My Kind of Blues, Release Date: 1961-08-18
//        Singer - Id: 1, First name: John, Last name: Mayer, Birthday: 1977-10-16
//              Album - id: 2, Singer id: 1, Title: Battle Studies, Release Date: 2009-11-17
//              Album - id: 1, Singer id: 1, Title: The Search For Everything, Release Date: 2017-01-20
//                  Instrument: Instrument :Guitar
//                  Instrument: Instrument :Piano
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//              Album - id: 3, Singer id: 2, Title: From The Cradle , Release Date: 1994-09-13
//                  Instrument: Instrument :Guitar

        System.out.println("Редактирование имеющейся записи");
        singer = singerService.findById(1L);
        Album album = singer.getAlbums().stream().filter(a -> a.getTitle().equals("Battle Studies")).findFirst().get();
        singer.setFirstName("John Clayton");
        singer.removeAlbum(album);
        singerService.save(singer);
        listSingersWithAlbum(singerService.findAllWithAlbum());
//        Singer - Id: 1, First name: John Clayton, Last name: Mayer, Birthday: 1977-10-16
//              Album - id: 1, Singer id: 1, Title: The Search For Everything, Release Date: 2017-01-20
//                  Instrument: Instrument :Guitar
//                  Instrument: Instrument :Piano
//        Singer - Id: 4, First name: BB, Last name: King, Birthday: 1940-09-16
//              Album - id: 4, Singer id: 4, Title: My Kind of Blues, Release Date: 1961-08-18
//              Album - id: 5, Singer id: 4, Title: A Heart Full of Blues, Release Date: 1962-04-20
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//              Album - id: 3, Singer id: 2, Title: From The Cradle , Release Date: 1994-09-13
//                  Instrument: Instrument :Guitar
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01

        System.out.println("Удаление записи:");
        singer = singerService.findById(1L);
        singerService.delete(singer);
        listSingersWithAlbum(singerService.findAllWithAlbum());
//        Singer - Id: 4, First name: BB, Last name: King, Birthday: 1940-09-16
//              Album - id: 4, Singer id: 4, Title: My Kind of Blues, Release Date: 1961-08-18
//              Album - id: 5, Singer id: 4, Title: A Heart Full of Blues, Release Date: 1962-04-20
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//              Album - id: 3, Singer id: 2, Title: From The Cradle , Release Date: 1994-09-13
//                  Instrument: Instrument :Guitar
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01

        System.out.println("Поиск всех певцов, собственный запрос");
        singers = singerService.findAllByNativeQuery();
        singers.forEach(System.out::println);
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01
//        Singer - Id: 4, First name: BB, Last name: King, Birthday: 1940-09-16

        System.out.println("Поиск всех певцов, собственный запрос с преобразованием результирующего набора SQL");
        singers = singerService.findAllByNativeQuerySQL();
        singers.forEach(System.out::println);
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01
//        Singer - Id: 4, First name: BB, Last name: King, Birthday: 1940-09-16

        System.out.println("Использование криетериев поиска"); //todo ошибка
        try {
            listSingersWithAlbum(singerService.findByCriteriaQuery("John", "Mayer"));
            listSingersWithAlbum(singerService.findByCriteriaQuery("John", null));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Finish");
    }

    private static void listSingersWithAlbum(List<Singer> singers) {
        singers.forEach(s -> {
            System.out.println(s);
            if (s.getAlbums() != null)
                s.getAlbums().forEach(a -> System.out.println("\t" + a));
            if (s.getInstruments() != null)
                s.getInstruments().forEach(i -> System.out.println("\t\tInstrument: " + i));
        });
    }
}
//еще есть: отслеживание аудиторских операций, версии сущностей