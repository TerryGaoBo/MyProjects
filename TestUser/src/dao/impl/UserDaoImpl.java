package dao.impl;


import dao.UserDao;
import domain.User;
import util.JDBCUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class UserDaoImpl implements UserDao {
    @Override
    public User login(User loginUser) {
        Connection connection = null;
        PreparedStatement ps  = null;
        ResultSet rs = null;
        User user = null;
        try {
            String sql = "select * from usermessage where username=? and password=?";
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, loginUser.getUsername());
            ps.setString(2,loginUser.getPassword());
            rs = ps.executeQuery();

            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setAddress(rs.getString("address"));
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("gender"));
                user.setQq(rs.getString("qq"));
                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }finally {
            JDBCUtils.close(connection,ps,rs);
        }
        return user;
    }

    @Override
    public List<User> findAll() {

        Connection connection = null;
        PreparedStatement ps  = null;
        ResultSet rs = null;

        List<User> users = new ArrayList<>();

        String sql = "select * from usermessage";
        try {
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setAddress(rs.getString("address"));
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("gender"));
                user.setQq(rs.getString("qq"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public int Add(User addUser) {
        Connection connection = null;
        PreparedStatement ps  = null;
        try {
            String sql="insert into usermessage(name,gender,age,address,qq,email) values(?,?,?,?,?,?)";
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, addUser.getName());
            ps.setString(2, addUser.getGender());
            ps.setInt(3,addUser.getAge());
            ps.setString(4, addUser.getAddress());
            ps.setString(5, addUser.getQq());
            ps.setString(6, addUser.getEmail());

            int ret = ps.executeUpdate();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int Delete(int id) {
        System.out.println("Delete："+id);
        //删除成功返回1
        Connection connection = null;
        PreparedStatement ps  = null;

        try {
            String sql="delete from usermessage where id=?";
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            int ret = ps.executeUpdate();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public User Find(int id) {

        Connection connection = null;
        PreparedStatement ps  = null;
        ResultSet rs = null;

        User user = null;
        String sql = "select * from usermessage where id=?";
        try {
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setAddress(rs.getString("address"));
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("gender"));
                user.setQq(rs.getString("qq"));
                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int Update(User updateUser) {
        Connection connection = null;
        PreparedStatement ps  = null;
        ResultSet rs = null;

        String sql = "update usermessage set name=?,age=?,gender=?,address=?,qq=?,email=? where id=?";
        try {
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, updateUser.getName());
            ps.setInt(2,updateUser.getAge());
            ps.setString(3, updateUser.getGender());

            ps.setString(4, updateUser.getAddress());
            ps.setString(5, updateUser.getQq());
            ps.setString(6, updateUser.getEmail());
            ps.setInt(7,updateUser.getId());

            int ret = ps.executeUpdate();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void setValues(PreparedStatement preparedStatement, Object... values) throws SQLException {
        System.out.println("====setValues==" + values.length);
        for (int i = 0; i < values.length; i++) {

            preparedStatement.setObject(i + 1, values[i]);

        }
    }
    //分页查询
    @Override
    public List<User> findByPage(int start, int rows,Map<String, String[]> map) {
        String sql="select * from usermessage where 1=1";
        StringBuilder s=new StringBuilder(sql);
        Set<String> set = map.keySet();
        List<Object> list =new ArrayList<>();
        for(String key  : set){
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
        System.out.println("list: "+ list);
        System.out.println("s: "+ s);

        Connection connection = null;
        PreparedStatement ps  = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {

            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(s.toString());
            setValues(ps,list.toArray());
            rs = ps.executeQuery();

            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setAddress(rs.getString("address"));
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("gender"));
                user.setQq(rs.getString("qq"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(connection,ps,rs);
        }
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
        Connection connection = null;
        PreparedStatement ps  = null;
        ResultSet rs = null;
        int count = 0;
        try {

            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(s.toString());
            setValues(ps,list.toArray());
            rs = ps.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);  //对总记录数赋值
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(connection,ps,rs);
        }
        return count;
    }
}
