package jdbc.sqlUpdate;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class DeleteSinger extends SqlUpdate { //расширение SqlUpdate, чтобы потом использовать updateByNamedParam
    private static final String SQL_DELETE_SINGERS = "delete from singer where id = :id";

    public DeleteSinger(DataSource dataSource) {
        super(dataSource, SQL_DELETE_SINGERS); //передать источник данных и запрос
        super.declareParameter(new SqlParameter("id", Types.INTEGER)); //передать имя параметра и тип
    }

}
