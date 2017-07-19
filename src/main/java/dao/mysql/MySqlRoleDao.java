package dao.mysql;

import dao.BaseDao;
import dao.RoleDao;
import dao.exception.DaoException;
import entity.Role;
import entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by User on 18.07.2017.
 */
public class MySqlRoleDao  extends BaseDao<Role> implements RoleDao{
    private static final String FIND_BY_CUSTOMER = "SELECT roles.role_id,roles.name FROM roles JOIN users ON  roles.role_id = users.role_id   WHERE users.user_id = ?";

    @Override
    public Role insert(Role item) throws DaoException {
        return null;
    }

    @Override
    public Role findById(int id) throws DaoException {
        return null;
    }

    @Override
    public void update(Role item) throws DaoException {

    }

    @Override
    public void delete(Role item) throws DaoException {

    }

    @Override
    public Role findRoleByUser(User user) throws DaoException {
        Role role= null;
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_CUSTOMER)){
                statement.setInt(1, user.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        role = itemRole(role, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("can't find by customer ", e);
        }
        return role;
    }

    private Role itemRole(Role role, ResultSet resultSet) throws SQLException {
        role = new Role();
        role.setId(resultSet.getInt(1));
        role.setName(resultSet.getString(2));
        return role;
    }

}
