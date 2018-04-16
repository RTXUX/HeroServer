package xyz.rtxux.demo1.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String token;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_current_question")
    private List<Question> currentQuestion;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_answered_question")
    private List<Question> answeredQuestions;
    private int capableOfPrize = 0;

    public User(String token, List<Question> currentQuestion) {
        this.token = token;
        this.currentQuestion = currentQuestion;
    }

    public User() {
    }

    public List<Question> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(List<Question> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Question> getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(List<Question> currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getCapableOfPrize() {
        return capableOfPrize;
    }

    public void setCapableOfPrize(int capableOfPrize) {
        this.capableOfPrize = capableOfPrize;
    }
}
