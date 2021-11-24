package jdbc;

import jdbc.config.EmbeddedJdbcConfig;
import jdbc.dao.JdbcSingerDao;
import jdbc.entities.Album;
import jdbc.entities.Singer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Конфигурация через XML");
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:embedded-h2-cfg.xml");
        ctx.refresh();
        JdbcSingerDao singerDao = ctx.getBean("singerDao", JdbcSingerDao.class);

        System.out.println("Получение имени певца по его Id без применения именованного параметра");
        String singerName = singerDao.findNameById(1l);
        System.out.println(singerName); //John Mayer

        System.out.println("Получение певца по его Id с применением именованного параметра");
        singerName = singerDao.findNameByIdNamedParameter(1l);
        System.out.println(singerName); //John Mayer

        System.out.println("Получение всех певцов с помощью RowMapper");
        List<Singer> singers = singerDao.findAll();
        singers.forEach(System.out::println);
//        Singer - Id: 1, First name: John, Last name: Mayer, Birthday: 1977-10-16
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01

        System.out.println("Получение всех певцов с помощью MappingSqlQuery");
        singers = singerDao.findAllMappingSqlQuery();
        singers.forEach(System.out::println);

        System.out.println("Получение всех певцов с альбомами");
        singers = singerDao.findAllWithAlbums();
        singers.forEach(singer -> {
            System.out.println(singer);
            if (singer.getAlbums() != null)
                singer.getAlbums().forEach(album -> System.out.println("   " + album));
        });
//        Singer - Id: 1, First name: John, Last name: Mayer, Birthday: 1977-10-16
//              Album - Id: 2, Singer id: 1, Title: Battle Studies, Release Date: 2009-11-17
//              Album - Id: 1, Singer id: 1, Title: The Search For Everything, Release Date: 2017-01-20
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//              Album - Id: 3, Singer id: 2, Title:  From The Cradle , Release Date: 1994-09-13
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01
        ctx.close();

        System.out.println("Конфигурация через Java");
        AnnotationConfigApplicationContext java = new AnnotationConfigApplicationContext(EmbeddedJdbcConfig.class);
        singerDao = java.getBean("singerDao", JdbcSingerDao.class);

        System.out.println("Получение всех певцов");
        singers = singerDao.findAllMappingSqlQuery();
        singers.forEach(System.out::println);
//        Singer - Id: 1, First name: John, Last name: Mayer, Birthday: 1977-10-16
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01

        System.out.println("Получение певцов по имени");
        singers = singerDao.findByFirstName("John");
        singers.forEach(System.out::println);
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01
//        Singer - Id: 1, First name: John, Last name: Mayer, Birthday: 1977-10-16

        System.out.println("Обновление певца");
        Singer singerUpdate = new Singer(1L, "Egor", "Kreed", new Date(90, 10, 10));
        singerDao.update(singerUpdate);
        singers = singerDao.findAll();
        singers.forEach(System.out::println);
//        Singer - Id: 1, First name: Egor, Last name: Kreed, Birthday: 1990-11-10
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01

        System.out.println("Добавление нового певца");
        Singer singerInsert = new Singer("Klava", "Koka", new Date(93, 12, 30));
        singerDao.insert(singerInsert);
        singers = singerDao.findAll();
        singers.forEach(System.out::println);
//        Singer - Id: 1, First name: Egor, Last name: Kreed, Birthday: 1990-11-10
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01
//        Singer - Id: 4, First name: Klava, Last name: Koka, Birthday: 1994-01-30

        System.out.println("Добавление нового певца с альбомами");
        Album album1 = new Album("Memory", new Date(10, 5, 30));
        Album album2 = new Album("Kawai", new Date(15, 7, 10));
        Singer singerWithAlbumInsert = new Singer("Selena", "Gomez", new Date(0, 0, 0));
            singerWithAlbumInsert.addAlbum(album1);
            singerWithAlbumInsert.addAlbum(album2);
        singerDao.insertWithAlbum(singerWithAlbumInsert);
        singers = singerDao.findAllWithAlbums();
        singers.forEach(singer -> {
            System.out.println(singer);
            if (singer.getAlbums() != null)
                singer.getAlbums().forEach(album -> System.out.println("   " + album));
        });
//        Singer - Id: 1, First name: Egor, Last name: Kreed, Birthday: 1990-11-10
//              Album - Id: 2, Singer id: 1, Title: Battle Studies, Release Date: 2009-11-17
//              Album - Id: 1, Singer id: 1, Title: The Search For Everything, Release Date: 2017-01-20
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//              Album - Id: 3, Singer id: 2, Title:  From The Cradle , Release Date: 1994-09-13
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01
//        Singer - Id: 4, First name: Klava, Last name: Koka, Birthday: 1994-01-30
//        Singer - Id: 5, First name: Selena, Last name: Gomez, Birthday: 1899-12-31
//              Album - Id: 5, Singer id: 5, Title: Kawai, Release Date: 1915-08-10
//              Album - Id: 4, Singer id: 5, Title: Memory, Release Date: 1910-06-30

        System.out.println("Удаление певца по айди:");
        singerDao.delete(3L);
        singers = singerDao.findAllWithAlbums();
        singers.forEach(singer -> {
            System.out.println(singer);
            if (singer.getAlbums() != null)
                singer.getAlbums().forEach(album -> System.out.println("   " + album));
        });
//        Singer - Id: 1, First name: Egor, Last name: Kreed, Birthday: 1990-11-10
//              Album - Id: 2, Singer id: 1, Title: Battle Studies, Release Date: 2009-11-17
//              Album - Id: 1, Singer id: 1, Title: The Search For Everything, Release Date: 2017-01-20
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//              Album - Id: 3, Singer id: 2, Title:  From The Cradle , Release Date: 1994-09-13
//        Singer - Id: 4, First name: Klava, Last name: Koka, Birthday: 1994-01-30
//        Singer - Id: 5, First name: Selena, Last name: Gomez, Birthday: 1899-12-31
//              Album - Id: 5, Singer id: 5, Title: Kawai, Release Date: 1915-08-10
//              Album - Id: 4, Singer id: 5, Title: Memory, Release Date: 1910-06-30

//        System.out.println("Вызов хранимой функции(проверять только на БД)");
//        String firstNameById = singerDao.findFirstNameById(1L);
//        System.out.println(firstNameById);

    }

}


