package hibernate.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity //класс-сущность, опитывает таблицу БД
@Table(name = "album") //имя таблицы
public class Album implements Serializable {
	private Long id;
	private String title;
	private Date releaseDate;
	private int version;
	private Singer singer;

	public Album() {}

	public Album(String title, Date releaseDate) {
		this.title = title;
		this.releaseDate = releaseDate;
	}

	@Id //первичный ключ
	@GeneratedValue(strategy = IDENTITY) //стратегия генерирования данных первичного ключа
	@Column(name = "ID") //имя колонки
	public Long getId() {
		return this.id;
	}

	@Version
	@Column(name = "VERSION")
	public int getVersion() {
		return version;
	}

	@ManyToOne //связь многие к одному с Певцом, задается другая сторона связи с Singer
	@JoinColumn(name = "SINGER_ID") //для столбца с именем внешнего ключа
	public Singer getSinger() {
		return this.singer;
	}

	@Column
	public String getTitle() {
		return this.title;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RELEASE_DATE")
	public Date getReleaseDate() {
		return this.releaseDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSinger(Singer singer) {
		this.singer = singer;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Album - Id: " + id + ", Singer id: " + singer.getId()
				+ ", Title: " + title + ", Release Date: " + releaseDate;
	}
}
