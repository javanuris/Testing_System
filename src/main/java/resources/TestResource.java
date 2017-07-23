package resources;

import entity.*;
import services.AnswerService;
import services.TestService;
import services.UserService;
import services.UserTestService;
import services.exceptions.ServiceException;
import utils.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Secured
@Path("/")
public class TestResource {

    public static final int ONE_HOUR = 60 * 60 * 1000;

    @Context
    SecurityContext securityContext;

    TestService testService = new TestService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Test getTest(@PathParam("id") Integer id) {
        return testService.findTestById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/test")
    public User test() {
        Principal principal = securityContext.getUserPrincipal();
        String userName = principal.getName();
        User user = new User();
        user.setPhone(userName);
        return user;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Test> getAllTests() {
        return testService.getAllTests();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Test createTest(Test test) {
        return testService.createTest(test);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/result")
    public Result resultPoint(ArrayList<Answer> answers) {
        UserTestService userTestService = new UserTestService();
        AnswerService answerService = new AnswerService();
        TestService testService = new TestService();

        Result result = answerService.pointCounter(answers);
        UserService userService = new UserService();

        User user = userService.findUserByPhone(securityContext.getUserPrincipal().getName());
        Test test = testService.findTestByAnswer(answers.get(0).getId());

        UserTest userTest = userTestService.findUserTestByLastTest(test.getId(), user.getId());

        if (test != null) {
            try {
                if (userTest != null) {
                    if (userTestService.checkRangeOfTimeFromLastTesting(ONE_HOUR, new Date(), userTest.getEndDate())) {
                        userTestService.saveUserResult(user, result, answers.get(0));
                    } else {
                        return null;
                    }
                } else {
                    userTestService.saveUserResult(user, result, answers.get(0));
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

 

    @Path("/{id}/questions")
    public QuestionResource getQuestion() {
        return new QuestionResource();
    }
}
