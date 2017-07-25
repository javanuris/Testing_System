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

    public void saveUserResult(User user, Result result, Answer answer, int answerCount, UserTest userTestInfo) throws ServiceException {
        int pass = 0;
        int precent = 0;
        TestService testService = new TestService();

        UserTest userTest = new UserTest();
        Test test = testService.findTestByAnswer(answer.getId());

        if (result.getPointCount() <= 0) {

        } else {
            precent = percentageOfPoints(answerCount, result.getPointCount());
        }

        if (precent >= test.getPercentage()) {
            pass = 1;
        } else {
            pass = 0;
        }

        if (test != null) {
            userTest.setTest(test);
        } else {
            throw new ServiceException("Test can not be null");
        }
        if (user != null) {
            userTest.setUser(user);
            userTest.setPoints(result.getPointCount());
            userTest.setPass(pass);
            if(userTestInfo == null){
                userTest.setAttempt(1);
            }else{
                userTest.setAttempt(userTestInfo.getAttempt() + 1);
            }
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

    private int percentageOfPoints(int countQuestions, int countRightQuestions) {
        return (countRightQuestions * 100) / countQuestions;
    }

    public Integer countByPassAttemptTest(int pass , int attempt, int testId){
        Integer countByParameters = null;
        Integer result = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserTestDao userTestDao = (UserTestDao) daoFactory.getDao(daoFactory.typeDao().getUserTestDao());

                    countByParameters = userTestDao.countByPassAttemptTest(pass , attempt , testId);
                if(countByParameters!=null || countByParameters>=1) {
                    result = (countByParameters * 100) / countByTest(testId , attempt);

                }else{
                    result = 0;
                }
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Integer countByTest( int testId, int attempt){
        Integer integer = null;

        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserTestDao userTestDao = (UserTestDao) daoFactory.getDao(daoFactory.typeDao().getUserTestDao());
                integer = userTestDao.countByTest(testId , attempt);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return integer;
    }
}
