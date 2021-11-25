package hibernate.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity //это преобразуемый тип сущности
@Table(name = "singer") //имя таблицы в БД, в которую преобразуется эта сущность. Имена таблиц, столбцов могут быть опущены, если имена типов данных и атрибутов совпадают с именами таблиц и столбцов
@NamedQueries({ //список именованных запросов
		@NamedQuery(name="Singer.findById", //имя запроса
				query="select distinct s from Singer s " + //сам запрос - вернуть одного Певца
						"left join fetch s.albums a " + //немедленно произвести выборку связи с Альбомом
						"left join fetch s.instruments i " + //немедленно произвести выборку связи с Инструментом
						"where s.id = :id"), //по совпадающему id
		@NamedQuery(name="Singer.findAllWithAlbum", //как прошлый, только вернуть всех Певцов с Альбомами
				query="select distinct s from Singer s " +
						"left join fetch s.albums a " +
						"left join fetch s.instruments i")
})
public class Singer implements Serializable {
	private Long id;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private int version;
	private Set<Album> albums = new HashSet<>();
	private Set<Instrument> instruments = new HashSet<>();

	public Singer() {}

	public Singer(String firstName, String lastName, Date birthDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}

	@Id //первичный ключ объекта
	@GeneratedValue(strategy = IDENTITY) //идентификатор сгенерирован во время вставки данных
	@Column(name = "ID") //имя столбца
	public Long getId() {
		return this.id;
	}

	@Version //требуется применить механизм оптимистичной блокировки
	@Column(name = "VERSION")
	public int getVersion() {
		return version;
	}

	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return this.firstName;
	}

	@Column(name = "LAST_NAME")
	public String getLastName() {
		return this.lastName;
	}

	@Temporal(TemporalType.DATE) //тип java.util.Date делательно преобразовать в тип java.sql.Date
	@Column(name = "BIRTH_DATE")
	public Date getBirthDate() {
		return birthDate;
	}

	@OneToMany( //отношение один ко многим с классом Album
//			fetch = FetchType.EAGER, //извлечь данные из связей, но производительность будет хуже
			mappedBy = "singer", //свойство singer из класса Album, обеспечивающее связь (по определению внешнего ключа из таблицы FK_ALBOM_SIGNER)
			cascade=CascadeType.ALL, //операция обновления должна распространяться каскадом на порожденные записи
			orphanRemoval=true) //после обновления сведений об альбомах записи, которые больше не существуют, должны быть удалены из БД
	public Set<Album> getAlbums() {
		return albums;
	}

	@ManyToMany //связь многие Певцы ко многим Инструментам
	@JoinTable( //для указания промезжуточной таблицы для соединения
			name = "singer_instrument", //имя промезжуточной таблицы для соединения
			joinColumns = @JoinColumn(name = "SINGER_ID"), //столбец с внешним ключем для таблицы SINGER
			inverseJoinColumns = @JoinColumn(name = "INSTRUMENT_ID")) //столбец с внешним ключем для таблицы INSTRUMENT на другой стороне устанавливаемой связи
	public Set<Instrument> getInstruments() {
		return instruments;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String toString() {
		return "Singer - Id: " + id + ", First name: " + firstName
				+ ", Last name: " + lastName + ", Birthday: " + birthDate;
	}

	public boolean addAlbum(Album album) {
		album.setSinger(this);
		return getAlbums().add(album);
	}

	public void removeAlbum(Album album) {
		getAlbums().remove(album);
	}

	public void setAlbums(Set<Album> albums) {
		this.albums = albums;
	}

	public void setInstruments(Set<Instrument> instruments) {
		this.instruments = instruments;
	}

	public boolean addInstrument(Instrument... instruments) {
		for (Instrument instrument : instruments)
			getInstruments().add(instrument);
		return true;
	}


}
