package xyz.rtxux.demo1.ReturnModel;

public class IncorrectAnswer {
    private int qid;
    private int aid;

    public IncorrectAnswer(int qid, int aid) {
        this.qid = qid;
        this.aid = aid;
    }

    public IncorrectAnswer() {
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }
}
