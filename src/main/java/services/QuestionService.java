package services;

import dao.QuestionDao;
import dao.exception.DaoException;
import dao.manager.DaoFactory;
import entity.Answer;
import entity.Question;
import entity.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12.07.2017.
 */
public class QuestionService {
    public Question createQuestion(Question question) {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                QuestionDao questionDao = (QuestionDao) daoFactory.getDao(daoFactory.typeDao().getQuestionDao());
                daoFactory.startTransaction();
                questionDao.insert(question);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                e.printStackTrace();
            }
        }
        return question;
    }

    public Question findQuestionById(int id) {
        Question question = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                QuestionDao questionDao = (QuestionDao) daoFactory.getDao(daoFactory.typeDao().getQuestionDao());
                question = questionDao.findById(id);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return question;
    }

    public List<Question> findQuestionByTestId(int id) {
        List<Question> questionsFirst;
        List<Question> questionsSecond = new ArrayList<>();

        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                QuestionDao questionDao = (QuestionDao) daoFactory.getDao(daoFactory.typeDao().getQuestionDao());
                AnswerService answerService = new AnswerService();
                Test test = new Test();
                test.setId(id);
                questionsFirst = questionDao.findQuestionByTest(test);
                for(Question question : questionsFirst){
                    List<Answer> answers = answerService.findAnswerByQuestionId(question.getId());
                    if(answers!=null) {
                        question.setAnswers(answers);
                    }
                    questionsSecond.add(question);
                }

            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return questionsSecond;
    }
}