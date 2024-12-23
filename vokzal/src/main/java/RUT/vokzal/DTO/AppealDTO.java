package RUT.vokzal.DTO;

import java.io.Serializable;

public class AppealDTO  implements Serializable {
    public int id;
    private String header;
    private String question;
    private int feedback;
    private String dateStart;
    private String answer;
    private int userId;

    public AppealDTO() {}

    public AppealDTO(int id, String header,String question, int feedback, String dateStart, int userId, String answer) {
        this.id = id;
        this.header = header;
        this.question = question;
        this.feedback = feedback;
        this.dateStart = dateStart;
        this.userId = userId;
        this.answer = answer;
    }
    public int getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }
    public String getQuestion() {
        return question;
    }

    public int getFeedback() {
        return feedback;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getAnswer() {
        return answer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHeader(String header) {
        this.header = header;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
