package dao.mysql;

import dao.BaseDao;
import dao.QuestionDao;
import dao.exception.DaoException;
import entity.Question;
import entity.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by User on 12.07.2017.
 */
public class MySqlQuestionDao extends BaseDao<Question> implements QuestionDao{

    private static final String FIND_BY_ID = "SELECT * FROM questions WHERE question_id = ?";
    private static final String INSERT = "INSERT INTO questions VALUES(question_id,?,?)";
    private static final String UPDATE = "UPDATE questions SET name = ?, test_id = ? WHERE question_id = ?";
    private static final String DELETE = "DELETE FROM questions WHERE question_id = ?";
    private static final String FIND_BY_TEST = "SELECT questions.question_id, questions.name FROM questions JOIN tests ON tests.test_id  = questions.test_id WHERE tests.test_id = ?";


    @Override
    public Question insert(Question item) throws DaoException {
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
    public Question findById(int id) throws DaoException {
        Question question = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        question = itemTest(question, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find by id  ", e);
        }
        return question;
    }

    @Override
    public void update(Question item) throws DaoException {

    }

    @Override
    public void delete(Question item) throws DaoException {

    }

    @Override
    public Question findQuestionByTest(Test test) throws DaoException {
        Question question = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_TEST)) {
                statement.setInt(1, test.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        question = itemTest(question, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find by id  ", e);
        }
        return question;
    }

    private Question itemTest(Question question, ResultSet resultSet) throws SQLException {
        question = new Question();
        question.setId(resultSet.getInt(1));
        question.setName(resultSet.getString(2));
        return question;
    }

    private PreparedStatement statementTest(PreparedStatement statement, Question item) throws SQLException {
        statement.setString(1, item.getName());
        statement.setInt(2 , item.getTest().getId());
        return statement;
    }



}
