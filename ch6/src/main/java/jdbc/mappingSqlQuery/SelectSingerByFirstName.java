package jdbc.mappingSqlQuery;

import jdbc.entities.Singer;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class SelectSingerByFirstName extends MappingSqlQuery<Singer> {
    private static final String SQL_FIND_BY_FIRST_NAME =  "select id, first_name, last_name, birth_date from singer where first_name = :first_name";

    public SelectSingerByFirstName(DataSource dataSource) {
        super(dataSource, SQL_FIND_BY_FIRST_NAME);
        super.declareParameter(new SqlParameter("first_name", Types.VARCHAR)); //задать параметр
    }

    @Override
    protected Singer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Singer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getDate("birth_date"));
    }
}
