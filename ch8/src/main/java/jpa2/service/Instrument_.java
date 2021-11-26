package jpa2.service;

import jpa2.entities.Instrument;
import jpa2.entities.Singer;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Instrument.class)
public abstract class Instrument_ {

	public static volatile SetAttribute<Instrument, Singer> singers;
	public static volatile SingularAttribute<Instrument, String> instrumentId;

}

