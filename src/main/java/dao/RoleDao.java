package dao;

import dao.exception.DaoException;
import entity.Role;
import entity.User;

/**
 * Created by User on 18.07.2017.
 */
public interface RoleDao extends Dao<Role>{

    Role findRoleByUser(User user) throws DaoException;
}
