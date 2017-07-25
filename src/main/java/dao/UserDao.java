package dao;

import dao.exception.DaoException;
import entity.User;

import java.util.List;

/**
 * Created by User on 18.07.2017.
 */
public interface UserDao extends Dao<User> {
    User findUserByPhoneAndPassword(String phone, String password) throws DaoException;

    User findUserByPhone(String phone) throws DaoException;

    User findUserByToken(String token) throws DaoException;

    List<User> getAllUsers() throws DaoException;
}
