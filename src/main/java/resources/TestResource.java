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

    private static final int ONE_HOUR =  60*60*1000;
    private static final int AVAILABLE_TEST_TRUE = 1;
    private static final int AVAILABLE_TEST_FALSE = 0;
    private static final int FIRST_ELEMENT = 0;

    @Context
    SecurityContext securityContext;
    private TestService testService = new TestService();

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
    public Response resultPoint(ArrayList<Answer> answers) {
        UserTestService userTestService = new UserTestService();
        AnswerService answerService = new AnswerService();
        TestService testService = new TestService();

        Result result = answerService.pointCounter(answers);
        UserService userService = new UserService();

        User user = userService.findUserByPhone(securityContext.getUserPrincipal().getName());
        Test test = testService.findTestByAnswer(answers.get(FIRST_ELEMENT).getId());

        UserTest userTest = userTestService.findUserTestByLastTest(test.getId(), user.getId());

        if (test != null) {
            try {
                if (userTest != null) {
                    if (userTestService.checkRangeOfTimeFromLastTesting(test.getTiming() * ONE_HOUR, new Date(), userTest.getEndDate())) {
                        userTestService.saveUserResult(user, result, answers.get(FIRST_ELEMENT), answers.size(), userTest);
                    } else {
                        return Response.status(Response.Status.BAD_REQUEST).entity("You have already passed the test, wait for the next session").build();
                    }
                } else {
                    userTestService.saveUserResult(user, result, answers.get(FIRST_ELEMENT),answers.size(), userTest);
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        return Response.ok(result).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/available/{testId}")
    public Response isAvailableTest(@PathParam("testId") Integer testId) {
        UserTestService userTestService = new UserTestService();
        UserService userService = new UserService();
        TestService testService = new TestService();
        Test test = testService.findTestById(testId);

        User user = userService.findUserByPhone(securityContext.getUserPrincipal().getName());
        UserTest userTest = userTestService.findUserTestByLastTest(testId, user.getId());

        if (test != null) {
            if (userTest != null && userTestService.checkRangeOfTimeFromLastTesting(test.getTiming() * ONE_HOUR, new Date(), userTest.getEndDate())) {
                return Response.ok(AVAILABLE_TEST_TRUE).build();
            } else {
                return Response.ok(AVAILABLE_TEST_FALSE).build();
            }
        } else {
            return Response.status(Response.Status.EXPECTATION_FAILED).entity("Test can't be null").build();
        }
    }

    @Path("/{id}/questions")
    public QuestionResource getQuestion() {
        return new QuestionResource();
    }
}
