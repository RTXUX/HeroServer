package xyz.rtxux.demo1.JsonModel;


import java.util.List;

public class JsonAnswer {
    private int uid;
    private String token;
    private List<AnswerToQuestion> answers;

    public JsonAnswer(int uid, String token, List<AnswerToQuestion> answers) {
        this.uid = uid;
        this.token = token;
        this.answers = answers;
    }

    public JsonAnswer() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<AnswerToQuestion> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerToQuestion> answers) {
        this.answers = answers;
    }
}
