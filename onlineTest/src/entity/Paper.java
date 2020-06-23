package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: GAOBO
 * Date: 2020-06-02
 * Time: 20:23
 */
public class Paper {
    private int id;//试卷ID
    private String createTime;
    private int userId;//用户ID，表明是谁创建的这张试卷

    //该list中存放试卷中的每个题目的相关信息
    private List<PaperItem> paperItemList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<PaperItem> getPaperItemList() {
        return paperItemList;
    }
}
