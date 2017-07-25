package dao.mysql;

import dao.BaseDao;
import dao.UserTestDao;
import dao.exception.DaoException;
import entity.Test;
import entity.User;
import entity.UserTest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 19.07.2017.
 */
public class MySqlUserTestDao extends BaseDao<UserTest> implements UserTestDao {
    private static final String FIND_BY_ID = "SELECT * FROM user_test WHERE user_test_id = ?";
    private static final String INSERT = "INSERT INTO user_test VALUES(user_test_id,?,?,?,?,?,?)";
    private static final String FIND_BY_USER = "SELECT * FROM user_test WHERE user_id = ?";
    private static final String FIND_BY_TEST = "SELECT * FROM user_test WHERE test_id = ?";
    private static final String FIND_BY_LAST_DATE = "SELECT user_test.test_id , user_test.end_date , user_test.points,user_test.pass,user_test.attempt,user_test.user_id ,user_test.test_id FROM user_test INNER JOIN tests ON tests.test_id  = user_test.test_id where tests.test_id = ? and user_test.user_id = ? ORDER BY user_test.end_date  DESC LIMIT 1";
    private static final String COUNT_BY_PASS_ATTEMPT_TEST = "SELECT COUNT(*) FROM user_test where user_test.pass = ? and user_test.attempt = ? and user_test.test_id = ?;";
    private static final String COUNT_BY_TEST = "SELECT COUNT(*) FROM user_test where user_test.test_id = ? and user_test.attempt = ?;";

    @Override
    public UserTest insert(UserTest item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statementTest(statement, item);
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    item.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("can't insert " + item, e);
        }
        return item;
    }

    @Override
    public UserTest findById(int id) throws DaoException {
        UserTest userTest = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        userTest = itemTest(userTest, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find by id  ", e);
        }
        return userTest;
    }

    @Override
    public void update(UserTest item) throws DaoException {
    }

    @Override
    public void delete(UserTest item) throws DaoException {
    }

    @Override
    public List<UserTest> findUserTestByUser(User user) throws DaoException {
        List<UserTest> userTests = new ArrayList<>();
        UserTest userTest = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_USER)) {
                statement.setInt(1, user.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        userTest = itemTest(userTest, resultSet);
                        userTests.add(userTest);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find by id  ", e);
        }
        return userTests;
    }



    @Override
    public UserTest findUserTestByLastTest(Test test, User user) throws DaoException {
        UserTest userTest = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_LAST_DATE)) {
                statement.setInt(1, test.getId());
                statement.setInt(2, user.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        userTest = itemTest(userTest, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find by id  ", e);
        }
        return userTest;
    }

    @Override
    public Integer countByPassAttemptTest(int pass, int attempt, int testId) throws DaoException {
       Integer integer = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(COUNT_BY_PASS_ATTEMPT_TEST)) {
                statement.setInt(1, pass);
                statement.setInt(2 ,attempt);
                statement.setInt(3 , testId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        integer =resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find by parameters", e);
        }
        return integer;
    }

    @Override
    public Integer countByTest(int testId , int attempt) throws DaoException {
        Integer integer = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(COUNT_BY_TEST)) {
                statement.setInt(1 , testId);
                statement.setInt(2 , attempt);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        integer =resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find by parameters", e);
        }
        return integer;
    }

    private UserTest itemTest(UserTest userTest, ResultSet resultSet) throws SQLException {
        userTest = new UserTest();
        userTest.setId(resultSet.getInt(1));
        userTest.setEndDate(resultSet.getTimestamp(2));
        userTest.setPoints(resultSet.getInt(3));
        userTest.setPass(resultSet.getInt(4));
        userTest.setAttempt(resultSet.getInt(5));
        return userTest;
    }

    private PreparedStatement statementTest(PreparedStatement statement, UserTest item) throws SQLException {
        statement.setTimestamp(1, new Timestamp(item.getEndDate().getTime()));
        statement.setInt(2, item.getPoints());
        statement.setInt(3, item.getPass());
        statement.setInt(4, item.getAttempt());
        statement.setInt(5, item.getUser().getId());
        statement.setInt(6, item.getTest().getId());
        return statement;
    }

}
