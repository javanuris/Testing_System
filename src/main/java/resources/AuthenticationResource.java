package resources;

import entity.Credentials;
import entity.User;
import services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;


@Path("/authentication")
public class AuthenticationResource {
    UserService userService = new UserService();

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response authenticateUser(Credentials credentials) {

        String phone = credentials.getUsername();
        String password = credentials.getPassword();

        try {
            authenticate(phone, password);
            String token = issueToken(phone);
            return Response.ok(token).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void authenticate(String phone, String password) throws Exception {
        User user = userService.findUserByPhoneAndPassword(phone, password);
        if(user != null){
            return;
        }else {
            throw new Exception();
        }
    }

    private String issueToken(String phone) {
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        User user1 = userService.findUserByPhone(phone);
        user1.setToken(token);
        userService.updateUser(user1);
        return token;
    }


}
