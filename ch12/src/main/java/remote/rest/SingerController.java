package remote.rest;

import remote.entities.Singer;
import remote.entities.Singers;
import remote.services.SingerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller //является контроллером Spring MVC
//@RestController снабжена аннотациями @Controller и @ResponseBody
@RequestMapping(value="/rest/singer") //контроллер будет сопоставлен со всеми Url в главном веб-контексте
public class SingerController {
    final Logger logger = LoggerFactory.getLogger(SingerController.class);

    @Autowired
    private SingerService singerService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/listdata", method = RequestMethod.GET) //указывается шаблон и сопоставляемый с ним метод доступа по протоколу http //@GetMapping(value = "/listdata")
    @ResponseBody //предписывает направлять все значения из методов в потом вывода http ответа, если используется @RestController, то не надо
    public Singers listData() {
        return new Singers(singerService.findAll());
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET) //@GetMapping(value = "/{id}")
    @ResponseBody
    public Singer findSingerById(@PathVariable Long id) { //связать переменную пути в URL с аргументом id //todo что если ошибка в преобразовании типов
        return singerService.findById(id);
    }

    @RequestMapping(value="/", method=RequestMethod.POST) //@PostMapping(value="/")
    @ResponseBody
    public Singer create(@RequestBody Singer singer) { //вынуждает автоматически привязать содержимое тела http запроса к объекту предметной области Singer
        logger.info("Creating singer: " + singer);
        singerService.save(singer);
        logger.info("Singer created successfully with info: " + singer);
        return singer;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT) //@PutMapping(value="/{id}")
    @ResponseBody
    public void update(@RequestBody Singer singer, @PathVariable Long id) {
        logger.info("Updating singer: " + singer);
        singerService.save(singer);
        logger.info("Singer updated successfully with info: " + singer);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE) //@DeleteMapping(value="/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        logger.info("Deleting singer with id: " + id);
        Singer singer = singerService.findById(id);
        singerService.delete(singer);
        logger.info("Singer deleted successfully");
    }
}
