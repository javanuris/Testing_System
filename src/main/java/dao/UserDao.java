package dao;

import dao.exception.DaoException;
import entity.User;

/**
 * Created by User on 18.07.2017.
 */
public interface UserDao extends Dao<User>{
    User findUserByPhoneAndPassword(String phone, String password)throws DaoException;
}
