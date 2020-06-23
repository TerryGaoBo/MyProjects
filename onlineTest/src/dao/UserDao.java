package dao;

import entity.User;
import util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: GAOBO
 * Date: 2020-06-03
 * Time: 15:15
 */
public class UserDao {
    /**
     * 登录
     * @param loginUser
     * @return
     */
    public User login(User loginUser) {
        Connection connection = null;
        PreparedStatement ps  = null;
        ResultSet rs = null;
        User user = null;
        try {
            String sql = "select * from user where username=? and password=?";
            connection = DBUtils.getConnection(true);
            ps = connection.prepareStatement(sql);
            ps.setString(1, loginUser.getUsername());
            ps.setString(2,loginUser.getPassword());
            rs = ps.executeQuery();

            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(connection,ps,rs);
        }
        return user;
    }
}
