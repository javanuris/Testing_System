import entity.Test;
import entity.User;
import entity.UserTest;
import services.UserService;
import services.UserTestService;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

/**
 * Created by User on 19.07.2017.
 */
public class Main {
    public static void main(String[] args) {

        UserTestService userTestService = new UserTestService();

        Test test = new Test();
        test.setId(1);

        User user = new User();
        user.setId(2);

        UserTest userTest1 = new UserTest();
        userTest1.setUser(user);
        userTest1.setTest(test);
        userTest1.setPoints(10);
        userTest1.setEndDate(new Date());
         userTestService.createTest(userTest1);

    }
}
