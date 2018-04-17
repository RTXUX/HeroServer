package xyz.rtxux.demo1.JsonModel;

public class AnswerToQuestion {
    private int question_id;
    private int answer_id;

    public AnswerToQuestion(int question_id, int answer_id) {
        this.question_id = question_id;
        this.answer_id = answer_id;
    }

    public AnswerToQuestion() {
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }
}
