package dao;

import dao.exception.DaoException;
import entity.Question;
import entity.Test;

/**
 * Created by User on 12.07.2017.
 */
public interface QuestionDao extends Dao<Question>{
    Question findQuestionByTest(Test test) throws DaoException;
}
