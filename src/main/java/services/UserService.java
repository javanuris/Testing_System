package services;

import dao.RoleDao;
import dao.UserDao;
import dao.exception.DaoException;
import dao.manager.DaoFactory;
import entity.Role;
import entity.User;
import json.entity.RegistrationJson;
import services.exceptions.ServiceException;

import javax.sql.rowset.serial.SerialException;
import java.util.List;

public class UserService {
    
    public User createUser(User user) {
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

    public User findUserByPhone(String phone) {
        User user = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = (UserDao) daoFactory.getDao(daoFactory.typeDao().getUserDao());
                user = userDao.findUserByPhone(phone);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public User findUserByToken(String token){
        User user = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = (UserDao) daoFactory.getDao(daoFactory.typeDao().getUserDao());
                user = userDao.findUserByToken(token);
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

    public void updateUser(User user){
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = (UserDao) daoFactory.getDao(daoFactory.typeDao().getUserDao());
                RoleDao roleDao = (RoleDao) daoFactory.getDao(daoFactory.typeDao().getRoleDao());
                Role role = roleDao.findRoleByUser(user);
                user.setRole(role);
                userDao.update(user);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
    }

    public void registrationUser(RegistrationJson registrationJson) throws ServiceException{
        if(registrationJson!=null) {
            if(registrationJson.getPassword().equals(registrationJson.getRepeatPassword())) {
                Role role = new Role();
                User user = new User();
                role.setId(registrationJson.getRoleId());
                user.setPhone(registrationJson.getPhone());
                user.setRole(role);
                user.setToken("0");
                user.setPassword(registrationJson.getPassword());
                createUser(user);
            }else{
                 throw new ServiceException("Not same password");
            }
        }else{
            throw new ServiceException("Registration can be null");
        }
    }
}
