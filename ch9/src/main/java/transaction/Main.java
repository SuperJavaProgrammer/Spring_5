package transaction;

import transaction.config.DataJpaConfig;
import transaction.config.ServicesConfig;
import transaction.entities.Singer;
import transaction.services.SingerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(ServicesConfig.class, DataJpaConfig.class);
        SingerService singerService = ctx.getBean(SingerService.class);
        List<Singer> singers = singerService.findAll();
        singers.forEach(System.out::println);

        Singer singer = singerService.findById(1L);
            singer.setFirstName("John Clayton");
            singer.setLastName("Mayer");
        singerService.save(singer);
        System.out.println("Singer saved successfully: " + singer);
        System.out.println("Singer count: " + singerService.countAll());

    }

}
