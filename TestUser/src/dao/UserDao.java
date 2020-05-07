package dao;

import domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    //登录方法
    public User login(User loginUser);

    //查询所有的方法
    public List<User> findAll();

    //添加方法
    public int Add(User addUser);
    //删除方法
    public int Delete(int id);
    //根据指定id查询信息
    public User Find(int id);
    //更新方法
    public int Update(User updateUser);
    //根据索引查询   分页查询
    public List<User> findByPage(int start,int rows,Map<String, String[]> map);
    //查询一共有多少条记录
    public int findAllRecord(Map<String, String[]> map);
}
