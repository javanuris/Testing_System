package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12.07.2017.
 */
public class Question extends BaseEntity{
    private String name;
    private Test test;
    private List<Answer> answers = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
