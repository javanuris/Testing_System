package services;


import dao.AnswerDao;
import dao.exception.DaoException;
import dao.manager.DaoFactory;
import entity.Answer;
import entity.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12.07.2017.
 */
public class AnswerService {

    public Answer createQuestion(Answer answer) throws DaoException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                AnswerDao answerDao = (AnswerDao) daoFactory.getDao(daoFactory.typeDao().getAnswerDao());
                if(answer.getRight() == null){
                    answer.setRight(0);
                }
                daoFactory.startTransaction();
                answerDao.insert(answer);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                e.printStackTrace();
            }
        }
        return answer;
    }

    public Answer findAnswerById(int id) {
        Answer answer = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                AnswerDao answerDao = (AnswerDao) daoFactory.getDao(daoFactory.typeDao().getAnswerDao());
                answer = answerDao.findById(id);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return answer;
    }
    public List<Answer> findAnswerByQuestionId(int id) {
        List<Answer> answers = new ArrayList<>();
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                AnswerDao answerDao = (AnswerDao) daoFactory.getDao(daoFactory.typeDao().getAnswerDao());
                Question question = new Question();
                question.setId(id);
                answers = answerDao.findAnswerByQuestion(question);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return answers;
    }
}
