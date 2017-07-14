package dao.manager;

import connection.ConnectionPool;
import dao.mysql.MySqlAnswerDao;
import dao.mysql.MySqlQuestionDao;
import dao.mysql.MySqlTestDao;

/**
 * The class serves to initiate the objects, depending on the type of database.
 *
 * @author Kalenov Nurislam
 */
public class TypeDao {
    private static TypeDao typeDao;
    /**
     * Property, provides the name (type) of the database.
     */
    private ConnectionPool connectType;
    /**
     * Name (type) of the database.
     */
    private static final String MYSQL = "mysql";

    private TypeDao() {
        connectType = ConnectionPool.getInstance();
    }

    public Class getTestDao() {
        if (connectType.getType().equalsIgnoreCase(MYSQL)) {
            return MySqlTestDao.class;
        } else {
            return MySqlTestDao.class;
        }
    }

    public Class getQuestionDao() {
        if (connectType.getType().equalsIgnoreCase(MYSQL)) {
            return MySqlQuestionDao.class;
        } else {
            return MySqlQuestionDao.class;
        }
    }

    public Class getAnswerDao() {
        if (connectType.getType().equalsIgnoreCase(MYSQL)) {
            return MySqlAnswerDao.class;
        } else {
            return MySqlAnswerDao.class;
        }
    }

    public static TypeDao getInstance() {
        if (typeDao == null) {
            typeDao = new TypeDao();
        }
        return typeDao;
    }
}
