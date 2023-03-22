package DAO;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private Database db;
    private User user;
    private UserDao userDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Create a new Database
        db = new Database();
        // Create a new user with random data
        user = new User("arvih", "securePass01", "arvih@byu.edu", "Arvi", "Haxhillari", "m", "00001");
        // Open database connection
        Connection conn = db.getConnection();
        //Then we pass that connection to the UserDAO, so it can access the database.
        userDao = new UserDao(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        userDao.clear();
    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database between test cases.
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        userDao.insert(user);
        User compareTest = userDao.find(user.getUsername());
        assertNotNull(compareTest);
        assertEquals(user, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        userDao.insert(user);
        assertThrows(DataAccessException.class, () -> userDao.insert(user));
    }

    @Test
    public void findPass() throws DataAccessException {
        userDao.insert(user);
        User compareTest = userDao.find(user.getUsername());
        assertNotNull(compareTest);
        assertEquals(user, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        User compareTest = userDao.find(user.getUsername());
        assertNull(compareTest);
    }

    @Test
    public void clearPass() throws DataAccessException {
        userDao.insert(user);
        User tempPerson = userDao.find(user.getUsername());
        userDao.clear();
        User fakePerson = userDao.find(user.getUsername());

        assertNotNull(tempPerson);
        assertNull(fakePerson);
    }

    @Test
    public void testClear() throws DataAccessException {
        User user = new User("arvih", "password", "testuser@example.com", "Test", "User", "m", "1234");
        userDao.insert(user);
        userDao.clear();
        assertNull(userDao.find("arvih"), "User should not be found in the database after clear");
    }
}