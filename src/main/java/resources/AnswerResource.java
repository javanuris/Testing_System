package resources;

import entity.Answer;
import services.AnswerService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/")
public class AnswerResource {
    private AnswerService answerService = new AnswerService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Answer> getAnswers(@PathParam("id") Integer id) {
        return answerService.findAnswerByQuestionId(id);
    }


}
