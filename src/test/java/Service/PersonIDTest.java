package Service;

import DataAccess.DataAccessException;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Result.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PersonIDTest {
    private PersonsService personsService;
    private FillService fillService;
    private PersonIDService personIDService;

    private RegisterService registerService;

    @BeforeEach
    void setUp() throws DataAccessException {
        personIDService = new PersonIDService();
        personsService = new PersonsService();
        fillService = new FillService();
        registerService = new RegisterService();
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        new ClearService().clear();
    }

    @Test
    void testGetPersonByIdSuccess() throws DataAccessException {
        User user = new User("arvih", "passWord","test@email.com", "arvi", "haxhillari","m", "12345");
        RegisterResult response = registerService.register(new RegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getGender()));
        String authToken = response.getAuth_token();

        fillService.fill(user.getUsername(), 4);

        PersonsResult responsePersons = personsService.getAllPersons(authToken);
        Person[] persons = responsePersons.getData();

        String randomID = persons[new Random().nextInt(30)].getPersonID();
        PersonIDResult responseID = personIDService.getPersonById(randomID, authToken);

        assertEquals(responseID.getPersonID(), randomID);
    }

    @Test
    void testGetPersonByIdInvalidAuthToken() throws DataAccessException, SQLException {
        // Register a new user
        User user = new User("johndoe", "password", "johndoe@example.com", "John", "Doe", "m", "12345");
        RegisterResult registerResult = registerService.register(new RegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getGender()));
        String authToken = registerResult.getAuth_token();

        fillService.fill(user.getUsername(), 0);
        PersonIDResult resID = personIDService.getPersonById(user.getPersonID(), authToken);

        assertNull(resID.getPersonID());
    }

}
