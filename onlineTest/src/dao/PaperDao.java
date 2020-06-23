package dao;

import entity.*;
import util.DBUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: GAOBO
 * Date: 2020-06-02
 * Time: 19:48
 */
public class PaperDao {

    /**
     * 录题
     * @param item
     * @return
     */
    public  int insertItem(Item item) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "insert into itembank(title, contentA, contentB, contentC, contentD, answer,score) VALUES (?,?,?,?,?,?,?)";
            connection = DBUtils.getConnection(true);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,item.getTitle());
            preparedStatement.setString(2,item.getContentA());
            preparedStatement.setString(3,item.getContentB());
            preparedStatement.setString(4,item.getContentC());
            preparedStatement.setString(5,item.getContentD());
            preparedStatement.setString(6,item.getAnswer());
            preparedStatement.setDouble(7,item.getScore());

            int ret = preparedStatement.executeUpdate();
            if(ret == 1) {
                return 1;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除题库一条题目，通过题目ID
     * @param item_id
     * @return
     */
    public  int deleteItemId(int item_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "delete from itembank where id=?";
            connection = DBUtils.getConnection(true);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,item_id);
            int ret = preparedStatement.executeUpdate();
            if(ret == 1) {
                return 1;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 更新题目信息
     * @param item
     * @return
     */
    public  int updateItem(Item item) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "update itembank set title=?,contentA=?,contentB=?,contentC=?,contentD=?,answer=?,score=? where id=?";
            connection = DBUtils.getConnection(true);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,item.getTitle());
            preparedStatement.setString(2,item.getContentA());
            preparedStatement.setString(3,item.getContentB());
            preparedStatement.setString(4,item.getContentC());
            preparedStatement.setString(5,item.getContentD());
            preparedStatement.setString(6,item.getAnswer());
            preparedStatement.setDouble(7,item.getScore());
            preparedStatement.setInt(8,item.getId());

            int ret = preparedStatement.executeUpdate();
            if(ret == 1) {
                return 1;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 通过题目id在题库中查询：题目
     * @param item_id
     * @return
     */
    public  Item queryItemById(int item_id) {
        Item item = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "select * from itembank where id=?";
            connection = DBUtils.getConnection(true);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,item_id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                item = new Item();
                item.setId(resultSet.getInt("id"));
                item.setTitle(resultSet.getString("title"));
                item.setContentA(resultSet.getString("contentA"));
                item.setContentB(resultSet.getString("contentB"));
                item.setContentC(resultSet.getString("contentC"));
                item.setContentD(resultSet.getString("contentD"));
                item.setScore(resultSet.getDouble("score"));
                item.setAnswer(resultSet.getString("answer"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }


    public List<Item> findItemByTitle(String title) {
        List<Item> itemList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtils.getConnection(true);
            preparedStatement = connection.prepareStatement("select*from itembank where title like '%"+title+"%'");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Item item = new Item();
                item.setId(resultSet.getInt("id"));
                item.setTitle(resultSet.getString("title"));
                item.setContentA(resultSet.getString("contentA"));
                item.setContentB(resultSet.getString("contentB"));
                item.setContentC(resultSet.getString("contentC"));
                item.setContentD(resultSet.getString("contentD"));
                item.setScore(resultSet.getDouble("score"));
                item.setAnswer(resultSet.getString("answer"));
                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtils.close(connection, preparedStatement, resultSet);
        }
        return itemList;
    }

    public List<Item> findItem() {
        List<Item> itemList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtils.getConnection(true);
            preparedStatement = connection.prepareStatement("select*from itembank");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Item item = new Item();
                item.setId(resultSet.getInt("id"));
                item.setTitle(resultSet.getString("title"));
                item.setContentA(resultSet.getString("contentA"));
                item.setContentB(resultSet.getString("contentB"));
                item.setContentC(resultSet.getString("contentC"));
                item.setContentD(resultSet.getString("contentD"));
                item.setScore(resultSet.getDouble("score"));
                item.setAnswer(resultSet.getString("answer"));
                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtils.close(connection, preparedStatement, resultSet);
        }
        return itemList;
    }

    /**
     * 录入试卷：
     * 1、试卷库表：paper表插入一条数据
     * 2、中间表paperitem：插入试卷项信息
     * @param paper 试卷内容
     * @return true 代表录入成功
     */
    public  boolean recordPaper(Paper paper) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            String insertPaperSql = "insert into paper(create_time, user_id) values (?,?)";
            String insertPaperItemSql = "insert into paperitem(paper_id, item_id) VALUES (?,?)";
            connection = DBUtils.getConnection(false);
            preparedStatement = connection.prepareStatement(insertPaperSql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,paper.getCreateTime());
            preparedStatement.setInt(2,paper.getUserId());



            if( preparedStatement.executeUpdate() == 0) {
                throw new RuntimeException("插入试卷失败！");
            }
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id = -1;
            if(rs.next()) {
                 id = rs.getInt(1);
            }

            //开始插入试卷详情表
            preparedStatement = connection.prepareStatement(insertPaperItemSql);
            for (PaperItem paperItem : paper.getPaperItemList()) {
                if(id == -1) throw new RuntimeException("id值为-1");
                preparedStatement.setInt(1, id);
                //preparedStatement.setInt(1,paperItem.getPaperId());
                preparedStatement.setInt(2,paperItem.getItemId());
                preparedStatement.addBatch();
            }
            int[] effects = preparedStatement.executeBatch();
            for (int i : effects) {
                if(i == 0) {
                    throw new RuntimeException("插入试卷明细失败！");
                }
            }
            //手动提交
            connection.commit();
        }catch (SQLException e) {
            e.printStackTrace();
            if(connection != null) {
                try {
                    //回滚
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return false;
        }
        return true;
    }

    /**
     * 查询所有试卷列表
     * @return
     */
    public List<Paper> findPaper() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Paper> papers = new ArrayList<>();
        try {
            //String sql = "select id from paper where user_id=?";
            String sql = "select * from paper " ;

            connection = DBUtils.getConnection(true);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Paper paper = new Paper();
                paper.setId(resultSet.getInt("id"));
                paper.setCreateTime(resultSet.getString("create_time"));
                paper.setUserId(resultSet.getInt("user_id"));
                papers.add(paper);
            }
            return papers;
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(connection,preparedStatement,resultSet);
        }
        return papers;

    }

    /**
     * 根据试卷ID查找试卷
     * @param paperId
     * @return
     */
    public List<Paper> findPaperById(int paperId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Paper> papers = new ArrayList<>();
        try {
            String sql = "select * from paper where paper.id = ?" ;

            connection = DBUtils.getConnection(true);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,paperId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Paper paper = new Paper();
                paper.setId(resultSet.getInt("id"));
                paper.setCreateTime(resultSet.getString("create_time"));
                paper.setUserId(resultSet.getInt("user_id"));
                papers.add(paper);
            }
            return papers;
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(connection,preparedStatement,resultSet);
        }
        return papers;

    }
    /**
     *根据试卷试卷ID查询试卷内容
     */
    public  List<Item> queryPaperById(int paperId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Item> itemList = new ArrayList<>();
        try {
            //String sql = "select id from paper where user_id=?";
            String sql = "select itembank.id as iid,title,contentA,contentB,contentC,contentD,score,answer  from paperitem pi,itembank where pi.item_id=itembank.id and paper_id = ?" ;

            connection = DBUtils.getConnection(true);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,paperId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
                item.setId(resultSet.getInt("iid"));
                item.setTitle(resultSet.getString("title"));
                item.setContentA(resultSet.getString("contentA"));
                item.setContentB(resultSet.getString("contentB"));
                item.setContentC(resultSet.getString("contentC"));
                item.setContentD(resultSet.getString("contentD"));
                item.setScore(resultSet.getDouble("score"));
                item.setAnswer(resultSet.getString("answer"));
                itemList.add(item);
            }
            return itemList;
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(connection,preparedStatement,resultSet);
        }
        return null;
    }



    /**
     * 判题
     * @return
     */
  public Item Judgement(int itemId,String answer) {
      System.out.println(itemId + "->" + answer);
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      Item item = null;
      try {
          String sql = "select * from itembank where id = ? and answer=?";
          connection = DBUtils.getConnection(true);
          preparedStatement = connection.prepareStatement(sql);
          preparedStatement.setInt(1,itemId);
          preparedStatement.setString(2,answer);
          resultSet = preparedStatement.executeQuery();
          if(resultSet.next()) {
              item = new Item();
              item.setScore(resultSet.getDouble("score"));
          }
      }catch (SQLException e) {
          e.printStackTrace();
      }finally {
          DBUtils.close(connection,preparedStatement,resultSet);
      }
      return item;
  }

    /**
     * 试卷做完后，提交试卷，包括试卷ID，考生ID，试卷总分
     * @param scoreTable
     * @return
     */
  public  boolean commitOverPaper(ScoreTable scoreTable) {
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
          String sql = "insert into scoretable(paper_id, user_id, totalscore) VALUES (?,?,?)";
          connection = DBUtils.getConnection(true);
          preparedStatement = connection.prepareStatement(sql);
          preparedStatement.setInt(1,scoreTable.getPaperId());
          preparedStatement.setInt(2,scoreTable.getUserId());
          preparedStatement.setDouble(3,scoreTable.getTotalScore());
          if(preparedStatement.executeUpdate() == 1) {
              return true;
          }
      }catch (SQLException  e) {
          e.printStackTrace();
      }finally {
          DBUtils.close(connection,preparedStatement,null);
      }
      return false;
  }


    /**
     * 查询这张试卷的，所有人的考试成绩
     * id
     * 试卷ID
     * 用户ID
     * 分数
     */
    public  List<ScoreTable> queryScore() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ScoreTable> scoreTableList = new ArrayList<>();

        try {
            String sql = "select sco.id,paper_id,user_id,totalscore,username  from scoretable as sco join user on user_id=user.id ";
            connection = DBUtils.getConnection(true);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ScoreTable scoreTable = new ScoreTable();
                scoreTable.setId(resultSet.getInt("sco.id"));
                scoreTable.setPaperId(resultSet.getInt("paper_id"));
                scoreTable.setUserId(resultSet.getInt("user_id"));
                scoreTable.setTotalScore(resultSet.getDouble("totalscore"));
                scoreTable.setUsername(resultSet.getString("username"));
                scoreTableList.add(scoreTable);
            }
            System.out.println("=============scoreTableList:==========="+ scoreTableList);
            return scoreTableList;
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(connection,preparedStatement,resultSet);
        }
        return null;
    }

    public static void main(String[] args) {
        String str = "abc";
        switch (str) {
            case "ab":
                break;
            case "abc":
                break;
                default:
                    break;
        }
    }

}
