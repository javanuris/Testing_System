package dao.mysql;

import dao.BaseDao;
import dao.RoleDao;
import dao.exception.DaoException;
import entity.Role;

/**
 * Created by User on 18.07.2017.
 */
public class MySqlRoleDao  extends BaseDao<Role> implements RoleDao{


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
}
