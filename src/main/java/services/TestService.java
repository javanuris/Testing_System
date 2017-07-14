package services;

import dao.TestDao;
import dao.exception.DaoException;
import dao.manager.DaoFactory;
import entity.Test;

import java.util.List;


public class TestService {

    public Test createTest(Test test) {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                TestDao testDao = (TestDao) daoFactory.getDao(daoFactory.typeDao().getTestDao());
                daoFactory.startTransaction();
                testDao.insert(test);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                e.printStackTrace();
            }
        }
        return test;
    }

    public Test findTestById(int id) {
        Test test = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                TestDao testDao = (TestDao) daoFactory.getDao(daoFactory.typeDao().getTestDao());
                test = testDao.findById(id);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return test;
    }

    public List<Test> getAllTests() {
        List<Test> tests = null;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                TestDao testDao = (TestDao) daoFactory.getDao(daoFactory.typeDao().getTestDao());
                tests = testDao.getAllTests();
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return tests;
    }
}
