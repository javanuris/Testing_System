import entity.User;
import services.UserService;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by User on 19.07.2017.
 */
public class Main {
    public static void main(String[] args) {
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        System.out.println(token);

        UserService userService = new UserService();
        User user = userService.findUserByToken("token1");
        System.out.println(user.getId()+ "/" + user.getToken() + "/" + user.getPhone()+ "/" + user.getPassword()+"/"+user.getRole());



    }
}
