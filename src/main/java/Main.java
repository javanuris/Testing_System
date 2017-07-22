import entity.Test;
import entity.UserTest;
import services.AnswerService;
import services.TestService;
import services.UserTestService;

import java.util.Date;

/**
 * Created by User on 21.07.2017.
 */
public class Main {
    public static void main(String[] args) {
        UserTestService userTestService = new UserTestService();
        UserTest userTest = userTestService.findUserTestById(2);

        Date date = new Date();

        System.out.println((date.getTime() - userTest.getEndDate().getTime()) /(60*60*1000));

        TestService testService =new TestService();
        Test test = testService.findTestByAnswer(8);
        System.out.println(test.getId());

    }

}
