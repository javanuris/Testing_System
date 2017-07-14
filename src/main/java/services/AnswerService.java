package services;


import dao.AnswerDao;
import dao.exception.DaoException;
import dao.manager.DaoFactory;
import entity.Answer;

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

}
