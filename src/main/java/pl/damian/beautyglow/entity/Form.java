package pl.damian.beautyglow.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_form")
public class Form {
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name="question1")
    private String question1;

    @Column(name="question2")
    private String question2;

    @Column(name="question3")
    private String question3;

    @Column(name="question4")
    private String question4;

    @Column(name="question5")
    private String question5;

    @Column(name="question6")
    private String question6;

    @Column(name="question7")
    private String question7;

    public Form() {
    }

    public Form(String question1, String question2, String question3, String question4, String question5, String question6, String question7) {
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.question4 = question4;
        this.question5 = question5;
        this.question6 = question6;
        this.question7 = question7;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getQuestion4() {
        return question4;
    }

    public void setQuestion4(String question4) {
        this.question4 = question4;
    }

    public String getQuestion5() {
        return question5;
    }

    public void setQuestion5(String question5) {
        this.question5 = question5;
    }

    public String getQuestion6() {
        return question6;
    }

    public void setQuestion6(String question6) {
        this.question6 = question6;
    }

    public String getQuestion7() {
        return question7;
    }

    public void setQuestion7(String question7) {
        this.question7 = question7;
    }
}
