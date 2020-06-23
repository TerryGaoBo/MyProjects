package entity;
/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: GAOBO
 * Date: 2020-06-03
 * Time: 13:05
 */
public class ScoreTable {
    private int id;
    private int paperId;
    private int userId;
    private double totalScore;

    private String username;//用户名称

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ScoreTable{" +
                "id=" + id +
                ", paperId=" + paperId +
                ", userId=" + userId +
                ", totalScore=" + totalScore +
                ", username='" + username + '\'' +
                '}';
    }
}
