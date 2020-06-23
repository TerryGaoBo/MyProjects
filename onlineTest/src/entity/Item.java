package entity;
/**
 * Created with IntelliJ IDEA.
 * Description:
 *    题目实体类
 * User: GAOBO
 * Date: 2020-06-02
 * Time: 19:49
 */
public class Item {
    private int id;
    private String title;//题目标题
    private String contentA;//题目选项A
    private String contentB;//题目选项B
    private String contentC;//题目选项C
    private String contentD;//题目选项D
    private double score;//该题分数
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentA() {
        return contentA;
    }

    public void setContentA(String contentA) {
        this.contentA = contentA;
    }

    public String getContentB() {
        return contentB;
    }

    public void setContentB(String contentB) {
        this.contentB = contentB;
    }

    public String getContentC() {
        return contentC;
    }

    public void setContentC(String contentC) {
        this.contentC = contentC;
    }

    public String getContentD() {
        return contentD;
    }

    public void setContentD(String contentD) {
        this.contentD = contentD;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contentA='" + contentA + '\'' +
                ", contentB='" + contentB + '\'' +
                ", contentC='" + contentC + '\'' +
                ", contentD='" + contentD + '\'' +
                ", score=" + score +
                ", answer='" + answer + '\'' +
                '}';
    }
}
