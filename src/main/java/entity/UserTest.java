package entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by User on 19.07.2017.
 */
public class UserTest extends BaseEntity{

    private Date endDate;
    private Integer points;
    private User user;
    private Test test;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
