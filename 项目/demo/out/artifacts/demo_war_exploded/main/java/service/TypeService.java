package service;

import Dao.TypeDao;
import Utils.PageTool;
import entity.TypeDb;

import java.util.List;

/**
 * 图书类别
 */
public class TypeService {
    private TypeDao typeDao=new TypeDao();

    public PageTool<TypeDb> listPage (String currentPage, String pageSize){
        return typeDao.listPage(currentPage,pageSize);
    }

    public List<TypeDb> list(String tid, String typeName){
        return typeDao.list(tid,typeName);
    }

    public Integer addType(String typeName){return typeDao.addType(typeName);}

    public Integer updateType(TypeDb typeDb){return typeDao.updateType(typeDb);}

    public int delType(Integer tid){return typeDao.delType(tid);}


}
