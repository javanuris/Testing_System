package dao.mysql;

import dao.BaseDao;
import dao.UserDao;
import dao.exception.DaoException;
import entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by User on 18.07.2017.
 */
public class MySqlUserDao extends BaseDao<User> implements UserDao {
    private static final String FIND_BY_ID = "SELECT * FROM users WHERE user_id = ?";
    private static final String FIND_BY_TOKEN = "SELECT * FROM users WHERE token = ?";
    private static final String FIND_BY_PHONE = "SELECT * FROM users WHERE phone = ?";
    private static final String INSERT = "INSERT INTO users VALUES (user_id,?,?,?,?,?,?,?)";
    private static final String FIND_BY_PHONE_PASSWORD = "SELECT * FROM users WHERE phone = ? AND password = ?";
    private static final String UPDATE = "UPDATE users SET phone = ?,password = ?,token= ?,first_name =? , last_name = ? profession= ?,role_id = ? WHERE user_id = ?";

    @Override
    public User insert(User item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statementCustomer(statement, item);
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    item.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("can't insert customer = {}" + item, e);
        }
        return item;
    }

    @Override
    public User findById(int id) throws DaoException {
        User user = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        user = itemCustomer(user, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("can't find by id ", e);
        }
        return user;
    }


    @Override
    public void update(User item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(UPDATE)) {
                statementCustomer(statement, item);
                statement.setInt(5, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DaoException("can't update customer = {} " + item, e);
        }
    }

    @Override
    public void delete(User item) throws DaoException {

    }

    @Override
    public User findUserByPhoneAndPassword(String phone, String password) throws DaoException {
        User user = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_PHONE_PASSWORD)) {
                statement.setString(1, phone);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        user = itemCustomer(user, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("can't get by login and password ", e);
        }
        return user;
    }

    @Override
    public User findUserByPhone(String phone) throws DaoException {
        User user = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_PHONE)) {
                statement.setString(1, phone);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        user = itemCustomer(user, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("can't find by id ", e);
        }
        return user;
    }

    @Override
    public User findUserByToken(String token) throws DaoException {
        User user = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_TOKEN)) {
                statement.setString(1, token);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        user = itemCustomer(user, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("can't find by id ", e);
        }
        return user;
    }


    private PreparedStatement statementCustomer(PreparedStatement statement, User item) throws SQLException {
        statement.setString(1, item.getPhone());
        statement.setString(2, item.getPassword());
        statement.setString(3, item.getToken());
        statement.setString(4, item.getFirstName());
        statement.setString(5, item.getLastName());
        statement.setString(6, item.getProfession());
        statement.setInt(7, item.getRole().getId());
        return statement;
    }

    private User itemCustomer(User user, ResultSet resultSet) throws SQLException {
        user = new User();
        user.setId(resultSet.getInt(1));
        user.setPhone(resultSet.getString(2));
        user.setPassword(resultSet.getString(3));
        user.setToken(resultSet.getString(4));
        user.setFirstName(resultSet.getString(5));
        user.setLastName(resultSet.getString(6));
        user.setProfession(resultSet.getString(7));
        return user;
    }

}
