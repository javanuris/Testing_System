package resources;
import entity.Test;
import services.TestService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/messages")
public class TestResource {

   TestService testService = new TestService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Test getMessages(@PathParam("id") Integer id)
    {
      return testService.findBookById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Test> getAllTests(){
        return testService.getAllTests();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Test createTest(Test test){
        return testService.createTest(test);
    }

    @DELETE
    @Path("/{param}")
    public Response deleteMsg(@PathParam("param") String msg) {
        String output = "DELETE:Jersey say : " + msg;
        return Response.status(200).entity(output).build();
    }
}
