package Dao;

import Utils.C3p0Tool;
import Utils.PageTool;
import entity.TypeDb;
import entity.UserDb;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeDao {
    QueryRunner qr = new QueryRunner(C3p0Tool.getDataSource());
    //开启驼峰自动转换
    BeanProcessor bean = new GenerousBeanProcessor();
    RowProcessor processor = new BasicRowProcessor(bean);

    /**
     * 图书列表   分页
     *
     * @return
     */
    public PageTool<TypeDb> listPage (String currentPage, String pageSize){

        try {
            StringBuffer listSql = new StringBuffer("select * ");
            StringBuffer countSql = new StringBuffer("select count(*) ");
            StringBuffer sql = new StringBuffer("from t_type ");
            //获取总记录数
            Long total= qr.query(countSql.append(sql).toString(),new ScalarHandler<Long>());
            //初始化分页工具
            PageTool<TypeDb> pageTools= new PageTool<>(total.intValue(),currentPage,pageSize);
            sql.append(" limit ?,? ");
            //查询当前页的数据

            List<TypeDb> list= qr.query(listSql.append(sql).toString(),
                    new BeanListHandler<TypeDb>(TypeDb.class,processor),
                    pageTools.getStartIndex(),
                    pageTools.getPageSize());
            pageTools.setRows(list);
            return pageTools;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return new PageTool<TypeDb>(0,currentPage,pageSize);
    }

    /**
     * 多条件查询
     * @param tid
     * @param typeName
     * @return
     */
    public List<TypeDb> list(String tid,String typeName){
        StringBuffer listSql = new StringBuffer("select * from t_type where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if(tid!=null&&tid!=""){
            listSql.append(" and tid = ? ");
            params.add(tid);
        }
        if(typeName!=null&&typeName!=""){
            listSql.append(" and type_name = ? ");
            params.add(typeName);
        }
        try {
            return qr.query(listSql.toString(),new BeanListHandler<TypeDb>(TypeDb.class,processor),params.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加图书类别
     * @param
     * @return
     */
    public Integer addType(String typeName){
        String sql="insert into t_type (type_name) values (?)";
        Object[] params = {typeName};
        try {
            return qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 修改图书分类
     * @param
     * @return
     */
    public Integer updateType(TypeDb typeDb){
        String sql="update t_type set type_name=? where tid=?";
        Object[] params = {typeDb.getTypeName(),typeDb.getTid()};
        try {
            return qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除图书类别
     * @param
     * @return
     */
    public int delType(Integer tid){
        String sql = "delete from t_type where tid=?";
        Object[] params = {tid};
        try {
            return qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
