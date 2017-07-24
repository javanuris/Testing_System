import entity.Test;
import services.TestService;
import services.UserTestService;


/**
 * Created by User on 23.07.2017.
 */
public class Main {

    public static void main(String[] args) {
        TestService testService = new TestService();
        UserTestService userTestService = new UserTestService();
        Test test = testService.findTestByAnswer(12);
        System.out.println(test.getPercentage());

        System.out.println(userTestService.percentageOfPoints(3 , 1));

    }
}


