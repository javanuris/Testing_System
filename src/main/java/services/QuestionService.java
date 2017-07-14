package services;

import dao.QuestionDao;
import dao.exception.DaoException;
import dao.manager.DaoFactory;
import entity.Question;
import entity.Test;

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

    public Question findQuestionByTestId(int id) {
        Question question = null;
        try (DaoFactory daoFactory = new DaoFactory()) {

            try {
                QuestionDao questionDao = (QuestionDao) daoFactory.getDao(daoFactory.typeDao().getQuestionDao());
                Test test = new Test();
                test.setId(id);
                question = questionDao.findQuestionByTest(test);

            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return question;
    }
}