package services;

import dao.UserDao;
import dao.exception.DaoException;
import dao.manager.DaoFactory;
import entity.Role;
import entity.User;

import java.util.List;

/**
 * Created by User on 18.07.2017.
 */
public class UserService {


    public User createTest(User user) {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao testDao = (UserDao) daoFactory.getDao(daoFactory.typeDao().getUserDao());
                daoFactory.startTransaction();
                testDao.insert(user);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                e.printStackTrace();
            }
        }
        return user;
    }

    public User findUserById(int id) {
        User user = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = (UserDao) daoFactory.getDao(daoFactory.typeDao().getUserDao());
                user = userDao.findById(id);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public User findUserByPhoneAndPassword(String phone, String password) {
        User user = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = (UserDao) daoFactory.getDao(daoFactory.typeDao().getUserDao());
                user = userDao.findUserByPhoneAndPassword(phone, password);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}
