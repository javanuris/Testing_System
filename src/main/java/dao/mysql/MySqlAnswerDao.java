package dao.mysql;

import dao.AnswerDao;
import dao.BaseDao;
import dao.exception.DaoException;
import entity.Answer;
import entity.Question;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by User on 12.07.2017.
 */
public class MySqlAnswerDao extends BaseDao<Answer> implements AnswerDao {

    private static final String FIND_BY_ID = "SELECT * FROM answers WHERE answer_id = ?";
    private static final String INSERT = "INSERT INTO answers VALUES(answer_id,?,?,?)";
    private static final String UPDATE = "UPDATE questions SET name = ?,right = ?,question_id = ? WHERE answer_id = ?";
    private static final String DELETE = "DELETE FROM answers WHERE answer_id = ?";
    private static final String FIND_BY_QUESTION = "SELECT answers.answer_id , answers.name, answers.right, answers.question_id FROM answers JOIN questions ON questions.question_id  = answers.question_id WHERE  questions.question_id = ?";


    @Override
    public Answer insert(Answer item) throws DaoException {
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statementAnswer(statement, item);
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
    public Answer findById(int id) throws DaoException {
        return null;
    }

    @Override
    public void update(Answer item) throws DaoException {

    }

    @Override
    public void delete(Answer item) throws DaoException {

    }

    @Override
    public Answer findAnswerByQuestion(Question question) throws DaoException {
        Answer answer = null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_QUESTION)) {
                statement.setInt(1, question.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        answer = itemAnswer(answer, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find by id  ", e);
        }
        return answer;
    }

    private Answer itemAnswer(Answer answer, ResultSet resultSet) throws SQLException {
        answer = new Answer();
        answer.setId(resultSet.getInt(1));
        answer.setName(resultSet.getString(2));
        answer.setRight(resultSet.getInt(3));
        return answer;
    }

    private PreparedStatement statementAnswer(PreparedStatement statement, Answer item) throws SQLException {
        statement.setString(1, item.getName());
        statement.setInt(2, item.getRight());
        statement.setInt(3, item.getQuestion().getId());
        return statement;
    }

}
