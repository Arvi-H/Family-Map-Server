package DAO;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTokenDaoTest {
    private Database db;
    private AuthToken authToken;
    private AuthTokenDao authTokenDao;

    @BeforeEach
    void setUp() throws DataAccessException {
        db = new Database();
        authToken = new AuthToken("000001", "arvih");
        Connection conn = db.getConnection();
        authTokenDao = new AuthTokenDao(conn);
        authTokenDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    void clearAuthToken() throws DataAccessException {
        authTokenDao.insert(authToken);
        authTokenDao.clear();
        assertNull(authTokenDao.find(authToken.getAuthtoken()));
    }

    @Test
    public void testClear() throws DataAccessException {
        authTokenDao.insert(authToken);
        authTokenDao.clear();
        assertNull(authTokenDao.find("arvih"));
    }

    @Test
    public void findPass() throws DataAccessException {
        authTokenDao.insert(authToken);
        assertNotNull(authTokenDao.find("000001"));
    }

    @Test
    public void findFail() throws DataAccessException {
        AuthToken foundAuthToken = authTokenDao.find("failToken");
        assertNull(foundAuthToken);
    }

    @Test
    void insertPass() throws DataAccessException {
        authTokenDao.insert(authToken);
        assertNotNull(authTokenDao.find(authToken.getAuthtoken()));
    }

    @Test
    void insertFail() throws DataAccessException {
        authTokenDao.insert(authToken);
        assertThrows(DataAccessException.class, ()-> authTokenDao.insert(authToken));
    }
}
