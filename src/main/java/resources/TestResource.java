package resources;

import entity.Answer;
import entity.Result;
import entity.Test;
import entity.User;
import services.AnswerService;
import services.TestService;
import utils.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Path("/")
public class TestResource {

    TestService testService = new TestService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Test getTest(@PathParam("id") Integer id) {
        return testService.findTestById(id);
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
    public Result resultPoint(ArrayList<Answer> answers)
    {
        AnswerService answerService = new AnswerService();
        return answerService.pointCounter(answers);
    }

    @Path("/{id}/questions")
    public QuestionResource getQuestion() {
        return new QuestionResource();
    }
}
