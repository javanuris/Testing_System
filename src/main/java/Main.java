import entity.Question;
import services.QuestionService;

import java.util.List;

/**
 * Created by User on 14.07.2017.
 */
public class Main {

    public static void main(String[] args) {
        QuestionService questionService = new QuestionService();
        List<Question> questions = questionService.findQuestionByTestId(6);
        Question question = new Question();
        question.setId(4);
        if(questions.contains(question)){
            System.out.println("true yes consist");
        }else {
            System.out.println("NOT");
        }
    }
}
