package services;

import dao.UserTestDao;
import dao.exception.DaoException;
import dao.manager.DaoFactory;
import entity.*;
import services.exceptions.ServiceException;

import java.util.Date;
import java.util.List;

/**
 * Created by User on 19.07.2017.
 */
public class UserTestService {

    public UserTest createTest(UserTest userTest) {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                userTest.setEndDate(new Date());
                UserTestDao userTestDao = (UserTestDao) daoFactory.getDao(daoFactory.typeDao().getUserTestDao());
                daoFactory.startTransaction();
                userTestDao.insert(userTest);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                e.printStackTrace();
            }
        }
        return userTest;
    }


    public UserTest findUserTestById(int id) {
        UserTest userTest = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserTestDao userTestDao = (UserTestDao) daoFactory.getDao(daoFactory.typeDao().getUserTestDao());
                userTest = userTestDao.findById(id);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return userTest;
    }

    public List<UserTest> findUserTestByUser(User user) {
        List<UserTest> userTests = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserTestDao userTestDao = (UserTestDao) daoFactory.getDao(daoFactory.typeDao().getUserTestDao());
                userTests = userTestDao.findUserTestByUser(user);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return userTests;
    }

    public void saveUserResult(User user, Result result, Answer answer) throws ServiceException {
        TestService testService = new TestService();
        UserTest userTest = new UserTest();
        Test test = testService.findTestByAnswer(answer.getId());
        if (test != null) {
            userTest.setTest(test);
        } else {
            throw new ServiceException("Test can not be null");
        }
        if (user != null) {
            userTest.setUser(user);
            userTest.setPoints(result.getPointCount());
        } else {
            throw new ServiceException("User can not be null");
        }
        createTest(userTest);
    }

    public UserTest findUserTestByLastTest(int testId, int userId) {
        Test test = new Test();
        test.setId(testId);

        User user = new User();
        user.setId(userId);

        UserTest userTest = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserTestDao userTestDao = (UserTestDao) daoFactory.getDao(daoFactory.typeDao().getUserTestDao());
                userTest = userTestDao.findUserTestByLastTest(test, user);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return userTest;
    }

    public boolean checkRangeOfTimeFromLastTesting(int time, Date currentDate, Date lastTestingDate) {
        if (currentDate.getTime() - lastTestingDate.getTime() > time) {
            return true;
        } else {
            return false;
        }
    }
}
