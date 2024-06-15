package Utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * C3p0工具类
 */
public class C3p0Tool {
    /**
     * 获取数据源
     */
    private static DataSource dataSource=new ComboPooledDataSource();
    public static DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 获取链接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection()
    {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
