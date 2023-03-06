package DAO;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDaoTest {
    private Database db;
    private Person person;
    private PersonDao personDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Create a new Database
        db = new Database();
        // Create a new person with random data
        person = new Person("00001", "arvih", "Arvi", "Haxhillari", "m", "fatherId", "motherId", "spouseId");
        // Open database connection
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        personDao = new PersonDao(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        personDao.clear();
    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database between test cases.
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        personDao.insert(person);
        Person compareTest = personDao.find(person.getPersonID());
        assertNotNull(compareTest);
        assertEquals(person, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        personDao.insert(person);
        assertThrows(DataAccessException.class, () -> personDao.insert(person));
    }

    @Test
    public void findPass() throws DataAccessException {
        personDao.insert(person);
        Person compareTest = personDao.find(person.getPersonID());
        assertNotNull(compareTest);
        assertEquals(person, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        Person compareTest = personDao.find(person.getPersonID());
        assertNull(compareTest);
    }

    @Test
    public void clearPass() throws DataAccessException {
        personDao.insert(person);
        Person tempPerson = personDao.find(person.getPersonID());
        personDao.clear();
        Person fakePerson = personDao.find(person.getPersonID());

        assertNotNull(tempPerson);
        assertNull(fakePerson);
    }
}