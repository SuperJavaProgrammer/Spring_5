package hibernate.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "instrument")
public class Instrument implements Serializable {
	private String instrumentId;
	private Set<Singer> singers = new HashSet<>();

	public Instrument() { }

	public Instrument(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	@Id
	@Column(name = "INSTRUMENT_ID")
	public String getInstrumentId() {
		return this.instrumentId;
	}

	@ManyToMany //связь многие Инструменты ко многим Певцам
	@JoinTable(name = "singer_instrument", //имя промезжуточной таблицы соединения Инструментов и Певцов
			joinColumns = @JoinColumn(name = "INSTRUMENT_ID"), //колонка первичного ключа исходной сущности
			inverseJoinColumns = @JoinColumn(name = "SINGER_ID")) //колонка первичного ключа сущности, с кем устанавливается связь
	public Set<Singer> getSingers() {
		return this.singers;
	}

	public void setSingers(Set<Singer> singers) {
		this.singers = singers;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	@Override
	public String toString() {
		return "Instrument :" + getInstrumentId();
	}
}