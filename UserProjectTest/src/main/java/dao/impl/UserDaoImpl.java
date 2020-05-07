package dao.impl;

import dao.UserDao;
import domain.User;
import util.JDBCUtils;
import javafx.collections.ObservableList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User login(User loginUser) {
        //定义sql
        try {
            String sql="Select * from usermessage where username=? and password=?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), loginUser.getUsername(), loginUser.getPassword());
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        //定义sql
        String sql="select * from usermessage";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public int Add(User addUser) {
        String sql="insert into usermessage values(null,?,null,null,?,?,?,?,?)";
        int i = template.update(sql, addUser.getName(), addUser.getGender(), addUser.getAge(), addUser.getAddress(), addUser.getQq(), addUser.getEmail());
        return i;
    }

    @Override
    public int Delete(int id) {
        String sql="delete from usermessage where id=?";
        int i = template.update(sql, id);
        return i;
    }

    @Override
    public User Find(int id) {
        String sql="select *from usermessage where id=?";
        User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        return user;
    }

    @Override
    public int Update(User updateUser) {
        String sql="update usermessage set name=?,age=?,gender=?,address=?,qq=?,email=? where id=?";
        int i = template.update(sql, updateUser.getName(), updateUser.getAge(), updateUser.getGender(), updateUser.getAddress(), updateUser.getQq(), updateUser.getEmail(),updateUser.getId());
        return i;
    }
    //分页查询
    @Override
    public List<User> findByPage(int start, int rows,Map<String, String[]> map) {
        String sql="select * from usermessage where 1=1";
        StringBuilder s=new StringBuilder(sql);
        Set<String> set = map.keySet();
        List<Object> list =new ArrayList<>();
        for(String key:set){
            if(key.equals("currentPage") || key.equals("rows")){
                continue;
            }
            String value=map.get(key)[0];
            if(value!=null && !"".equals(value)){
                //有值
                s.append(" and "+key+" like ? ");
                list.add("%"+value+"%");
            }
        }
        s.append(" limit ?,? ");
        list.add(start);
        list.add(rows);
        List<User> users = template.query(s.toString(), new BeanPropertyRowMapper<User>(User.class),list.toArray());
        return users;
    }
    //查询共有多少条记录
    @Override
    public int findAllRecord(Map<String, String[]> map) {
        String sql="select count(*) from usermessage where 1=1";
        StringBuilder s=new StringBuilder();
        s.append(sql);
        Set<String> keySet = map.keySet();
        List<Object> list=new ArrayList<>();
        for(String key:keySet){
            if(key.equals("currentPage") || key.equals("rows")){
                continue;
            }
            String value=map.get(key)[0];
            if(value!=null && !"".equals(value)){
                //有值
                s.append(" and "+key+" like ?");
                list.add("%"+value+"%");
            }
        }
        int aLong = template.queryForObject(s.toString(), int.class,list.toArray());
        return aLong;
    }

}
