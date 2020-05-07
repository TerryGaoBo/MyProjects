package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import domain.PageBean;
import domain.User;
import service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    //登录方法
    @Override
    public User login(User loginUser) {
        UserDao userDao =new UserDaoImpl();
        User user = userDao.login(loginUser);
        return user;
    }
    //查询所有
    @Override
    public List<User> findAll() {
        UserDao userDao=new UserDaoImpl();
        List<User> users = userDao.findAll();
        return users;
    }
    //添加方法
    @Override
    public int Add(User addUser) {
        UserDao userDao=new UserDaoImpl();
        int i = userDao.Add(addUser);
        return i;
    }
    //删除方法
    @Override
    public int Delete(int id) {
        UserDao userDao=new UserDaoImpl();
        int i = userDao.Delete(id);
        return i;
    }
    //根据id查询
    @Override
    public User Find(int id) {
        UserDao userDao=new UserDaoImpl();
        User user = userDao.Find(id);
        return user;
    }
    //更新方法
    @Override
    public int Update(User updateUser) {
        UserDao userDao=new UserDaoImpl();
        int i = userDao.Update(updateUser);
        return i;
    }

    @Override
    public PageBean<User> FindAllByPage(int currentPage,int rows,Map<String, String[]> map) {
        PageBean<User> pageBean=new PageBean<>();
        UserDao userDao=new UserDaoImpl();
        int totalPage;
        int record = userDao.findAllRecord(map);//查询了所有记录
        if(record%rows==0){
            totalPage=record/rows;
        }else{
            totalPage=record/rows+1;
        }
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
