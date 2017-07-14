package dao;

import dao.exception.DaoException;
import entity.Answer;
import entity.Question;

/**
 * Created by User on 12.07.2017.
 */
public interface AnswerDao extends Dao<Answer> {
    Answer findAnswerByQuestion(Question question) throws DaoException;
}
