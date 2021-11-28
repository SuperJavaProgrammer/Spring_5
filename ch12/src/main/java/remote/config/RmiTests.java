package remote.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import remote.entities.Singer;
import remote.services.SingerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

//@Ignore
@ContextConfiguration(classes = RmiClientConfig.class)
@RunWith(SpringRunner.class)
public class RmiTests {
	private static Logger logger = LoggerFactory.getLogger(RmiTests.class);

	public static void main(String[] args) {
		GenericApplicationContext ctx = new AnnotationConfigApplicationContext(RmiClientConfig.class);
		SingerService singerService = ctx.getBean(SingerService.class);
		List<Singer> singers = singerService.findAll();
		listSingers(singers);
		singers = singerService.findByFirstName("John");
		listSingers(singers);
	}

	@Autowired
	private SingerService singerService;

	@Test
	public void testRmiAll() {
		List<Singer> singers = singerService.findAll();
		assertEquals(3, singers.size());
		listSingers(singers);
	}

	@Test
	public void testRmiJohn() {
		List<Singer> singers = singerService.findByFirstName("John");
		assertEquals(2, singers.size());
		listSingers(singers);
	}

	private static void listSingers(List<Singer> singers){
		singers.forEach(s -> logger.info(s.toString()));
	}
}
