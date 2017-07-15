package resources;

import dao.exception.DaoException;
import entity.Answer;
import entity.Question;
import services.AnswerService;

import javax.ws.rs.*;
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


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{answerId}")
    public Answer getAnswerById(@PathParam("id") Integer id, @PathParam("answerId") Integer answerId) {
        List<Answer> answers = answerService.findAnswerByQuestionId(id);
        Answer answer = new Answer();
        answer.setId(answerId);
        if (answers.contains(answer)) {
            for (Answer answ : answers) {
                if (answ.getId() == answerId) {
                    answer = answ;
                }
            }
            return answer;
        }
        return null;
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Answer createQuestion(@PathParam("id") Integer id ,Answer answer){
        Question question = new Question();
        question.setId(id);
        answer.setQuestion(question);
        try {
            return answerService.createAnswer(answer);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }
}
