package Service;

import DataAccess.DataAccessException;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Result.PersonsResult;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PersonsTest {
    private PersonsService personsService;
    private PersonIDService personIDService;
    private FillService fillService;
    private RegisterService registerService;

    @BeforeEach
    void setUp() throws DataAccessException {
        personsService = new PersonsService();
        fillService = new FillService();
        registerService = new RegisterService();
        personIDService = new PersonIDService();
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        new ClearService().clear();
    }

    @Test
    void testGetAllPersonsSuccess() {
        User user = new User("arvih", "passWord","test@email.com", "arvi", "haxhillari","m", "12345");
        RegisterResult response = registerService.register(new RegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getGender()));
        String authToken = response.getAuth_token();

        fillService.fill(user.getUsername(), 4);
        PersonsResult responsePersons = personsService.getAllPersons(authToken);
        Person[] persons = responsePersons.getData();
        assertEquals(persons.length, 31);
    }

    @Test
    void testGetAllPersonsInvalidAuthToken() throws DataAccessException, SQLException {
        // Attempt to get persons with an invalid auth token
        PersonsResult responsePersons = personsService.getAllPersons("invalidAuthToken");
        // Check that an error message is returned
        assertNotNull(responsePersons.getMessage());
    }
}
