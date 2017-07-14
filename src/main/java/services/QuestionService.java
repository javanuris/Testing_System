package services;

import dao.QuestionDao;
import dao.exception.DaoException;
import dao.manager.DaoFactory;
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
        List<Question> questions = new ArrayList<>();
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                QuestionDao questionDao = (QuestionDao) daoFactory.getDao(daoFactory.typeDao().getQuestionDao());
                Test test = new Test();
                test.setId(id);
                questions = questionDao.findQuestionByTest(test);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return questions;
    }
}