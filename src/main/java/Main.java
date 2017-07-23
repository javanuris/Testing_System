import entity.*;
import services.AnswerService;
import services.TestService;
import services.UserService;
import services.UserTestService;
import services.exceptions.ServiceException;

import java.util.Date;

/**
 * Created by User on 23.07.2017.
 */
public class Main {

    public static void main(String[] args) {


        Result result = new Result();
        result.setPointCount(5);

        Answer answer= new Answer();
        answer.setId(3);

        UserTestService userTestService = new UserTestService();
        AnswerService answerService = new AnswerService();
        TestService testService = new TestService();

        UserService userService = new UserService();

        User user = userService.findUserByPhone("nuris");
        Test test = testService.findTestByAnswer(answer.getId());

        UserTest userTest = userTestService.findUserTestByLastTest(test.getId(), user.getId());

        System.out.println( user.getId() + "user");

        System.out.println( test.getId() + "test");
        System.out.println(userTest.getEndDate() + "/" + userTest.getPoints() + "/" +userTest.getId());

        if (test != null) {
            try {
                if (userTest != null) {
                    if (userTestService.checkRangeOfTimeFromLastTesting(60 * 60 * 1000, new Date(), userTest.getEndDate())) {
                        userTestService.saveUserResult(user, result, answer);
                    }
                } else {
                   userTestService.saveUserResult(user, result, answer);
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }


}


