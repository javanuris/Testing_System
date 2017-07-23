package dao;

import dao.exception.DaoException;
import entity.Test;
import entity.User;
import entity.UserTest;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by User on 19.07.2017.
 */
public interface UserTestDao  extends Dao<UserTest> {
   List<UserTest> findUserTestByUser(User user) throws DaoException;

   UserTest findUserTestByLastTest(Test test , User user) throws DaoException;
}
