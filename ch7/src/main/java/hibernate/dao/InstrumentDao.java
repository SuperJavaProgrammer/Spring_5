package hibernate.dao;

import hibernate.entities.Instrument;

public interface InstrumentDao { //операции для работы с Инструментом
	Instrument save(Instrument instrument);
}
