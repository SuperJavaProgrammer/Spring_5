package jpa2.service;

import jpa2.entities.Album;
import jpa2.entities.Singer;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Album.class)
public abstract class Album_ { //класс метамодели, генерируется благодаря hibernate-jpamodelgen. Не работает...

	public static volatile SingularAttribute<Album, Singer> singer;
	public static volatile SingularAttribute<Album, Date> releaseDate;
	public static volatile SingularAttribute<Album, Long> id;
	public static volatile SingularAttribute<Album, String> title;
	public static volatile SingularAttribute<Album, Integer> version;

}

