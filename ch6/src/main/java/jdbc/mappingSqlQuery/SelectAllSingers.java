package jdbc.mappingSqlQuery;

import jdbc.entities.Singer;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectAllSingers extends MappingSqlQuery<Singer> { //расширение MappingSqlQuery для маппинга, чтобы потом использовать execute/executeByNamedParam
    private static final String SQL_SELECT_ALL_SINGER =  "select id, first_name, last_name, birth_date from singer";

    public SelectAllSingers(DataSource dataSource) {
        super(dataSource, SQL_SELECT_ALL_SINGER); //передаем в конструктов источник данных и запрос
    }

    @Override
    protected Singer mapRow(ResultSet rs, int rowNum) throws SQLException { //смаппить результат выборки на возвращаемый объект Singer
        return new Singer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getDate("birth_date"));
    }
}
