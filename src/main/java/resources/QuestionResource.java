package resources;

import entity.Question;
import entity.Test;
import services.QuestionService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class QuestionResource {

    private QuestionService questionService = new QuestionService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Question> getQuestions(@PathParam("id") Integer id) {
        List<Question> questions = questionService.findQuestionByTestId(id);
        return questions;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{questionId}")
    public Question getQuestionById(@PathParam("id") Integer id, @PathParam("questionId") Integer questionId) {
        List<Question> questions = questionService.findQuestionByTestId(id);
        Question question = new Question();
        question.setId(questionId);
        if (questions.contains(question)) {
            for (Question quest : questions) {
                if (quest.getId() == questionId) {
                    question = quest;
                }
            }
            return question;
        }
        return null;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Question createQuestion(@PathParam("id") Integer id ,Question question){
        Test test = new Test();
        test.setId(id);
        question.setTest(test);
        System.out.println(question.getName() +"/"+question.getId());
        return questionService.createQuestion(question);
    }

    @Path("/{id}/answers")
    public AnswerResource getQuestion()
    {
        return new AnswerResource();
    }
}
