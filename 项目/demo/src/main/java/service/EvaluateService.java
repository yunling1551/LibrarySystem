package service;

import Dao.EvaluateDao;
import Utils.PageTool;
import entity.EvaluateDb;

public class EvaluateService {
    EvaluateDao evaluateDao = new EvaluateDao();
    /**
     * 列表 分页
     * @return
     */
    public PageTool<EvaluateDb> list(String currentPage, String pageSize, String word, Integer order, Integer uid){
        return evaluateDao.list(currentPage, pageSize, word, order,uid);
    }

    /**
     * 添加问题
     * @param
     * @return
     */
    public Integer addProblem(EvaluateDb evaluateDb){
        return evaluateDao.addProblem(evaluateDb);
    }

    /**
     * 删除问题
     * @return
     */
    public Integer delProblem(String pid){
        return evaluateDao.delProblem(pid);
    }
}
