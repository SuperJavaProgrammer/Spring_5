package configurationAndBootstrap.profile;

import configurationAndBootstrap.profile.config.HighschoolConfig;
import configurationAndBootstrap.profile.config.KindergartenConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.List;

public class ProfileDemo { //запустить с передачей аргумента VM options: -Dspring.profiles.active="kindergarten/highschool"
    public static void main(String... args) {
        System.out.println("Профили через xml");
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.getEnvironment().setActiveProfiles("kindergarten"); //установить программно профиль, чтобы не через VM options
        ctx.load("classpath:*-config.xml"); //подгружает 2 файла настроек highschool-config.xml и kindergarten-config.xml
        ctx.refresh();
        FoodProviderService foodProviderService = ctx.getBean("foodProviderService", FoodProviderService.class); //получить foodProviderService, который для разных профилей возвращает разные данные
        List<Food> lunchSet = foodProviderService.provideLunchSet();
        for (Food food: lunchSet)
            System.out.println("Food: " + food.getName()); //Food: Milk Food: Biscuits for -Dspring.profiles.active="kindergarten" //Food: Coke Food: Hamburger Food: French Fries for -Dspring.profiles.active="highschool"
        ctx.close();

        System.out.println("Профили через Java");
        GenericApplicationContext java = new AnnotationConfigApplicationContext(KindergartenConfig.class, HighschoolConfig.class); //загрузить 2 конфигурационных класса
//        java.getEnvironment().setActiveProfiles("highschool"); //не работает
        foodProviderService = java.getBean("foodProviderService", FoodProviderService.class); //получить foodProviderService, если не установить профиль, то не найдет No bean named 'foodProviderService' available
        lunchSet = foodProviderService.provideLunchSet();
        for (Food food : lunchSet)
            System.out.println("Food: " + food.getName());
    }
}
