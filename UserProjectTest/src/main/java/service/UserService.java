package service;

import domain.PageBean;
import domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    //声明一个登录的方法
    public User login(User loginUser);
    //查询所有的方法
    public List<User> findAll();
    //添加方法
    public int Add(User addUser);
    //删除方法
    public  int Delete(int id);
    //根据id查询信息
    public User Find(int id);
    //更新方法
    public int Update(User updateUser);
    //查询方法,返回一个PageBean对象
    public PageBean<User> FindAllByPage(int currentPage,int rows, Map<String, String[]> map);
    //查询多少条记录
}
