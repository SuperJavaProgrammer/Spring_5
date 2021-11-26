package jpa2.сrudJpaRepository;

import jpa2.entities.Album;
import jpa2.entities.Singer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.List;

public class SpringJPADemo {
    public static void main(String... args) {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(DataJpaConfig.class);
//        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(); //через XML
//        ctx.load("classpath:app-context-annotation.xml");
//        ctx.refresh();

        SingerService singerService = ctx.getBean("springJpaSingerService", SingerService.class);
        listSingers("Find all:", singerService.findAll());
//        Singer - Id: 1, First name: John, Last name: Mayer, Birthday: 1977-10-16
//        Singer - Id: 2, First name: Eric, Last name: Clapton, Birthday: 1945-03-30
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01
        listSingers("Find by first name:", singerService.findByFirstName("John"));
//        Singer - Id: 3, First name: John, Last name: Butler, Birthday: 1975-04-01
//        Singer - Id: 1, First name: John, Last name: Mayer, Birthday: 1977-10-16
        listSingers("Find by first and last name:", singerService.findByFirstNameAndLastName("John", "Mayer"));
//        Singer - Id: 1, First name: John, Last name: Mayer, Birthday: 1977-10-16

        System.out.println("Проверка специальных запросов");
        AlbumService albumService = ctx.getBean(AlbumService.class);
        List<Album> albums = albumService.findByTitle("The");
        albums.forEach(a -> System.out.println(a + ", Singer: "+ a.getSinger().getFirstName() + " " + a.getSinger().getLastName()));
//        Album - id: 1, Singer id: 1, Title: The Search For Everything, Release Date: 2017-01-20, Singer: John Mayer
//        Album - id: 3, Singer id: 2, Title: From The Cradle , Release Date: 1994-09-13, Singer: Eric Clapton

//        ctx.close(); //для GenericXmlApplicationContext
    }

    private static void listSingers(String message, List<Singer> singers) {
        System.out.println(message);
        singers.forEach(System.out::println);
    }
}
