package jdbc.entities;

import java.io.Serializable;
import java.sql.Date;

public class Album implements Serializable { //класс Альбома певца
    private Long id;
    private Long singerId;
    private String title;
    private Date releaseDate;

    public Album() {}

    public Album(String title, Date releaseDate) {
        this.title = title;
        this.releaseDate = releaseDate;
    }

    public Album(Long id, Long singerId, String title, Date releaseDate) {
        this.id = id;
        this.singerId = singerId;
        this.title = title;
        this.releaseDate = releaseDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setSingerId(Long singerId) {
        this.singerId = singerId;
    }

    public Long getSingerId() {
        return this.singerId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getReleaseDate() {
        return this.releaseDate;
    }

    @Override
    public String toString() {
        return "Album - Id: " + id + ", Singer id: " + singerId
            + ", Title: " + title + ", Release Date: " + releaseDate;
    }
}
