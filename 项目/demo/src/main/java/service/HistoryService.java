package service;

import Dao.HistoryDao;
import Utils.PageTool;
import entity.HistoryDb;

import java.sql.SQLException;

public class HistoryService {
    HistoryDao historyDao = new HistoryDao();
    public PageTool<HistoryDb> listByPage (String currentPage, String pageSize, Integer uid, Integer status){
        return historyDao.listByPage(currentPage, pageSize, uid, status);
    }

    public Integer updateHistory(HistoryDb historyDb) throws SQLException{
        return historyDao.updateHistory(historyDb);
    }
}
