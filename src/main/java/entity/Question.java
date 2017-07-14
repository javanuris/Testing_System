package entity;

/**
 * Created by User on 12.07.2017.
 */
public class Question extends BaseEntity{
    private String name;
    private Test test;

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
}
