package DAO;

import DataAccess.*;
import Model.Person;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static Network.RandomUUID.getRandomUUID;
import static org.junit.jupiter.api.Assertions.*;

public class PersonDaoTest {
    private Database db;
    private Person person;
    private PersonDao personDao;
    private UserDao userDao;
    private EventDao eventDao;
    User user;
    Connection conn;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Create a new Database
        db = new Database();
        // Create a new person with random data
        person = new Person("00001", "arvih", "Arvi", "Haxhillari", "m", "fatherId", "motherId", "spouseId");

        user = new User("arvih", "passWord","test@email.com", "arvi", "haxhillari","m", "12345");

        // Open database connection
        conn = db.getConnection();

        //Then we pass that connection to the EventDAO, so it can access the database.
        personDao = new PersonDao(conn);
        userDao = new UserDao(conn);
        eventDao = new EventDao(conn);

        //Let's clear the database as well so any lingering data doesn't affect our tests
        personDao.clear("arvih");
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
        personDao.clear(person.getAssociatedUsername());
        Person fakePerson = personDao.find(person.getPersonID());

        assertNotNull(tempPerson);
        assertNull(fakePerson);
    }

    @Test
    void testClear() throws DataAccessException {
        personDao.insert(person);
        personDao.clear(person.getAssociatedUsername());
        assertNull(personDao.find(person.getPersonID()));
    }

    @Test
    void generateTreePass() throws DataAccessException {
        userDao.insert(user);
        personDao.generateTree(user, person.getPersonID(), 1, eventDao);
        int count = personDao.findAllPersons(user.getUsername()).length;
        assertEquals(count, 3);
    }

    @Test
    void generateTreeFail() throws DataAccessException {
        userDao.insert(user);
        personDao.generateTree(user, person.getPersonID(), 1, eventDao);
        int count = personDao.findAllPersons(user.getUsername()).length;
        assertNotEquals(count, 4);
    }

    @Test
    void generateParentsPass() throws DataAccessException {
        userDao.insert(user);

        Person child = new Person(getRandomUUID(), user.getUsername(), "Test", "User", "m",null, null, null);
        personDao.insert(child);

        eventDao.generateEvent(user.getUsername(), getRandomUUID(), 2000, "birth");
        personDao.generateParents(user.getUsername(), getRandomUUID(), 2000, 2, eventDao, "Smith");

        // check if the number of persons generated is correct
        int count = personDao.findAllPersons(user.getUsername()).length;
        assertEquals(count, 15);
    }

    @Test
    void generateParentsFail() throws DataAccessException {
        userDao.insert(user);

        Person child = new Person(getRandomUUID(), user.getUsername(), "Test", "User", "m",null, null, null);
        personDao.insert(child);

        eventDao.generateEvent(user.getUsername(), getRandomUUID(), 2000, "birth");
        personDao.generateParents(user.getUsername(), getRandomUUID(), 2000, 2, eventDao, "Smith");

        // check if the number of persons generated is correct
        int count = personDao.findAllPersons(user.getUsername()).length;
        assertNotEquals(count, 7);
    }

    @Test
    void updateFatherIDPass() throws DataAccessException {
        User user = new User("testUsername", "testPassword", "test@gmail.com", "Test", "User", "m", getRandomUUID());
        userDao.insert(user);

        String personID = getRandomUUID();
        Person person = new Person(personID, user.getUsername(), "Test", "User", "m", null, null, null);
        personDao.insert(person);

        String fatherID = getRandomUUID();
        personDao.updateFatherID(personID, fatherID);

        Person retrievedPerson = personDao.find(personID);
        assertEquals(retrievedPerson.getFatherID(), fatherID);
    }

    @Test
    void updateFatherIDFail() throws DataAccessException {
        User user = new User("testUsername", "testPassword", "test@gmail.com", "Test", "User", "m", getRandomUUID());
        userDao.insert(user);

        String personID = getRandomUUID();
        Person person = new Person(personID, user.getUsername(), "Test", "User", "m", null, null, null);
        personDao.insert(person);

        personDao.updateFatherID(personID, null);

        Person retrievedPerson = personDao.find(personID);
        assertNull(retrievedPerson.getFatherID());
    }

    @Test
    void updateMotherIDPass() throws DataAccessException {
        User user = new User("testUsername", "testPassword", "test@gmail.com", "Test", "User", "m", getRandomUUID());
        userDao.insert(user);

        String personID = getRandomUUID();
        Person person = new Person(personID, user.getUsername(), "Test", "User", "m", null, null, null);
        personDao.insert(person);

        String motherID = getRandomUUID();
        personDao.updateMotherID(personID, motherID);

        Person retrievedPerson = personDao.find(personID);
        assertEquals(retrievedPerson.getMotherID(), motherID);
    }

    @Test
    void updateMotherIDFail() throws DataAccessException {
        User user = new User("testUsername", "testPassword", "test@gmail.com", "Test", "User", "m", getRandomUUID());
        userDao.insert(user);

        String personID = getRandomUUID();
        Person person = new Person(personID, user.getUsername(), "Test", "User", "m", null, null, null);
        personDao.insert(person);

        personDao.updateMotherID(personID, null);

        Person retrievedPerson = personDao.find(personID);
        assertNull(retrievedPerson.getMotherID());
    }

    @Test
    void testFindAllPersons() throws DataAccessException {
        Person person1 = new Person("123", "testuser", "John", "Doe", "m", null, null, null);
        Person person2 = new Person("456", "testuser", "Jane", "Doe", "f", null, null, null);
        Person person3 = new Person("789", "testuser", "Bob", "Smith", "m", null, null, null);

        personDao.insert(person1);
        personDao.insert(person2);
        personDao.insert(person3);

        Person[] persons = personDao.findAllPersons("testuser");

        assertEquals(3, persons.length);
    }

    @Test
    void testFindAllPersonsFail() throws DataAccessException {
        // Try to find persons for a non-existing user
        Person[] persons = personDao.findAllPersons("nonexistinguser");

        // The function should return null in this case
        assertNull(persons);
    }

}