package RUT.vokzal.Model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "appeal", schema = "public")
public class Appeal extends BaseEntity {
    private String header;
    private String question;
    private int feedback;
    private LocalDate dateStart;
    private String answer;

    private User userId;

    public Appeal() {}

    public Appeal(String header, String question, int feedback, LocalDate dateStart, User userId) {
        this.header = header;
        this.question = question;
        this.feedback = feedback;
        this.dateStart = dateStart;
        this.userId = userId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    public User getUserId() {
        return this.userId;
    }

    @Column(name = "header", nullable = false)
    public String getHeader() {
        return header;
    }
    @Column(name = "question", nullable = false)
    public String getQuestion() {
        return question;
    }

    @Column(name = "feedback", nullable = false)
    public int getFeedback() {
        return feedback;
    }

    @Column(name = "date_start", nullable = false)
    public LocalDate getDateStart() {
        return dateStart;
    }

    @Column(name = "answer", nullable = true)
    public String getAnswer() {
        return answer;
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

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setUserId(User user) {
        this.userId = user;
    }
}
