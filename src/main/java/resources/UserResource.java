package resources;

import entity.User;
import entity.UserTest;
import json.entity.StatisticsTesting;
import org.hibernate.stat.Statistics;
import services.UserService;
import services.UserTestService;
import utils.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;


@Secured
@Path("/user")
public class UserResource {
    @Context
    SecurityContext securityContext;

    private UserService userService = new UserService();
    private UserTestService userTestService = new UserTestService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public User getUser(@PathParam("id") Integer id) {
        return userService.findUserById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/tests")
    public List<UserTest> getUserTests(@PathParam("id") Integer id)
    {
        User user = new User();
        user.setId(id);
        return userTestService.findUserTestByUser(user);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/statistics")
    public Response statisticsTesting(StatisticsTesting statisticsTesting){
        UserTestService userTestService = new UserTestService();
        Integer resultInPercent = userTestService.countByPassAttemptTest(statisticsTesting.getPass(), statisticsTesting.getAttepmt() ,statisticsTesting.getTestId());
        return Response.ok(resultInPercent.toString()).build();
    }

    @GET
    @Produces("application/json")
    @Path("/statistics")
    public StatisticsTesting statisticsTesting(){
        StatisticsTesting statisticsTesting = new StatisticsTesting();
        statisticsTesting.setAttepmt(1);
        statisticsTesting.setPass(0);
        statisticsTesting.setTestId(3);
              return statisticsTesting;
    }
}
