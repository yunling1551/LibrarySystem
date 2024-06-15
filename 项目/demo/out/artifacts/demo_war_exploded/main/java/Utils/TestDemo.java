package Utils;

import Dao.BookDao;
import Dao.HistoryDao;
import Dao.UserDao;
import entity.BookDb;
import entity.HistoryDb;
import entity.UserDb;

import java.sql.SQLException;

public class TestDemo {
    public static void main(String[] args) throws SQLException {
        BookDao bookDao = new BookDao();
        HistoryDao historyDao = new HistoryDao();
        HistoryDb historyDb = new HistoryDb();
        historyDb.setHid(1);
        historyDb.setStatus(2);
        System.out.println(historyDao.updateHistory(historyDb,C3p0Tool.getConnection()));
    }
}
