package xyz.rtxux.demo1.JsonModel;

import xyz.rtxux.demo1.Model.Answer;

import java.util.List;

public class JsonQuestion {
    List<Answer> answers;
    private int qid;
    private String description;

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
