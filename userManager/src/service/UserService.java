package service;

import dao.UserDao;
import entity.PageBean;
import entity.User;

import java.util.List;
import java.util.Map;

public class UserService {
    //登录方法
    public User login(User loginUser) {
        UserDao userDao = new UserDao();
        User user = userDao.login(loginUser);
        //System.out.println("UserService "+ user);
        return user;
    }

    //添加方法
    public int add(User addUser) {
        UserDao userDao=new UserDao();
        int i = userDao.add(addUser);
        return i;
    }
    //删除方法
    public int delete(int id) {
        UserDao userDao=new UserDao();
        int i = userDao.delete(id);
        return i;
    }
    //根据id查询
    public User find(int id) {
        UserDao userDao=new UserDao();
        User user = userDao.find(id);
        return user;
    }
    //更新方法
    public int update(User updateUser) {
        UserDao userDao=new UserDao();
        int i = userDao.update(updateUser);
        return i;
    }

    /**
     * @param currentPage 当前页
     * @param rows 每页的行数
     * @param map  包含 name address email
     * @return
     *
     */
    public  PageBean<User> findAllByPage(int currentPage,int rows,Map<String, String[]> map) {

        PageBean<User> pageBean=new PageBean<>();

        UserDao userDao=new UserDao();
        //1、查询共有多少条记录
        int totalPage;
        int record = userDao.findAllRecord(map);

        //2、总共的页数 totalPage
        if(record%rows==0){
            totalPage=record/rows;
        }else{
            totalPage=record/rows+1;
        }

        /**
         *  每一页的开始位置 = (当前页数-1) * 行数
         *  第1页开始位置：（1-1）*5 = 0
         *  第2页开始位置：（2-1）*5 = 5
         *  start确定的其实是 开始查询的起始位置
         */
        int start=(currentPage-1)*rows;

        List<User> users = userDao.findByPage(start, rows, map);

        pageBean.setCurrentPage(currentPage);
        pageBean.setList(users);
        pageBean.setRows(rows);
        pageBean.setTotalCount(record);
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

}
