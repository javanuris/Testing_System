package resources;

import entity.Test;
import services.TestService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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

    @Path("/{id}/questions")
    public QuestionResource getQuestion() {
        return new QuestionResource();
    }
}
