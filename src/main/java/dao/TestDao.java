package dao;

import dao.exception.DaoException;
import entity.Answer;
import entity.Test;

import java.util.List;

/**
 * Created by User on 12.07.2017.
 */
public interface TestDao extends Dao<Test> {
    List<Test> getAllTests() throws DaoException;

    Test findTestByAnswer(Answer answer) throws  DaoException;
}
