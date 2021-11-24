package jdbc.sqlUpdate;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class InsertSingerAlbum extends BatchSqlUpdate { //использование BatchSqlUpdate(расширение SqlUpdate) для пакетного выполнения операций
    private static final String SQL_INSERT_SINGER_ALBUM = "insert into album (singer_id, title, release_date) values (:singer_id, :title, :release_date)";
    private static final int BATCH_SIZE = 10; //размер пакета

    public InsertSingerAlbum(DataSource dataSource) {
        super(dataSource, SQL_INSERT_SINGER_ALBUM); //передать источник данных и запрос
        declareParameter(new SqlParameter("singer_id", Types.INTEGER));
        declareParameter(new SqlParameter("title", Types.VARCHAR));
        declareParameter(new SqlParameter("release_date", Types.DATE));
        setBatchSize(BATCH_SIZE); //установить размер пакета для операции вставки, чтобы делать вставку flush, когда достигнет такого размера
    }

}
