package configurationAndBootstrap.config;

import configurationAndBootstrap.Singer;
import configurationAndBootstrap.messageDigest.MessageDigestFactoryBean;
import configurationAndBootstrap.messageDigest.MessageDigester;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class SingerConfig {

	@Lazy //ленивая инициализация, получать только когда запрашивают
	@Bean(initMethod = "init", destroyMethod = "close") //указание методов при создании компонента и при уничтожении
	Singer singerOne() {
		Singer singerOne = new Singer();
			singerOne.setName("John Mayer");
			singerOne.setAge(39);
		return singerOne;
	}

	@Lazy
	@Bean(initMethod = "init", destroyMethod = "close")
	Singer singerTwo() {
		Singer singerTwo = new Singer();
			singerTwo.setAge(72);
		return singerTwo;
	}

	@Lazy
	@Bean(initMethod = "init", destroyMethod = "close")
	Singer singerThree() {
		Singer singerThree = new Singer();
			singerThree.setName("John Butler");
		return singerThree;
	}

	@Bean
	public MessageDigestFactoryBean shaDigest() {
		MessageDigestFactoryBean factoryOne = new MessageDigestFactoryBean();
			factoryOne.setAlgorithmName("SHA1");
		return factoryOne;
	}

	@Bean
	public MessageDigestFactoryBean defaultDigest() { //"MD5"
		return new MessageDigestFactoryBean();
	}

	@Bean
	MessageDigester digester() throws Exception {
		MessageDigester messageDigester = new MessageDigester();
			messageDigester.setDigest1(shaDigest().getObject()); //получить MessageDigest из MessageDigestFactoryBean
			messageDigester.setDigest2(defaultDigest().getObject()); //нужно вызывать вручную
		return messageDigester;
	}

}
