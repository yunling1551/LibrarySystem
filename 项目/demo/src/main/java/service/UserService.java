package service;

import Utils.PageTool;
import entity.UserDb;
import Dao.UserDao;
import java.util.List;

/**
 * 用户业务层
 */
public class UserService {
    private UserDao userDao=new UserDao();

    /**
     * 登录
     * @param account
     * @param password
     * @return
     */
    public UserDb login(String account,String password){
        return userDao.login(account,password);
    }

    public PageTool<UserDb> list(String current, String pageSize){
        return userDao.list(current, pageSize);
    }
    public List<UserDb> getList(UserDb user){return userDao.getList(user);}


    /**
     * 用户添加
     * @param user
     * @return
     */
    public Integer addUser(UserDb user){
        return userDao.addUser(user);
    }

    /**
     * 管理员修改用户信息
     * @param user
     * @return
     */
    public Integer updateUser(UserDb user){return userDao.updateUser(user);}

    /**
     * 删除用户-物理删除
     */
    public Integer delUser(Integer id){return userDao.delUser(id);}

}
