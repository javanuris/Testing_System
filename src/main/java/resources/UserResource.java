package resources;

import entity.User;
import entity.UserTest;
import services.UserService;
import services.UserTestService;
import utils.Secured;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

/**
 * Created by User on 20.07.2017.
 */
@Secured
@Path("/user")
public class UserResource {
    @Context
    SecurityContext securityContext;

    private UserService userService = new UserService();
    private UserTestService userTestService =new UserTestService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserTest> getUserInfo(){
        User user = userService.findUserByPhone(securityContext.getUserPrincipal().getName());
        return userTestService.findUserTestByUser(user);
    }

}
