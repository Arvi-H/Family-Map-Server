package Service;

import DataAccess.DataAccessException;
import Model.Event;
import Model.User;
import Request.RegisterRequest;
import Result.EventsResult;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventsTest {
    private EventsService eventsService;
    private FillService fillService;
    private RegisterService registerService;

    @BeforeEach
    void setUp() throws DataAccessException {
        eventsService = new EventsService();
        fillService = new FillService();
        registerService = new RegisterService();
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        new ClearService().clear();
    }

    @Test
    void testGetAllEventsSuccess() {
        User user = new User("arvih", "passWord","test@email.com", "arvi", "haxhillari","m", "12345");
        RegisterResult response = registerService.register(new RegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getGender()));
        String authToken = response.getAuth_token();

        fillService.fill(user.getUsername(), 0);
        EventsResult responseAll = eventsService.getAllEvents(authToken);
        Event[] events = responseAll.getData();

        assertEquals(events.length, 1);
    }

    @Test
    void testGetAllEventsInvalidAuthToken() throws DataAccessException, SQLException {
        User user = new User("username", "password", "email", "first", "last", "m", "personID");
        registerService.register(new RegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getGender()));

        // Generate random authToken
        String authToken = String.valueOf(new Random().nextInt(1000));

        // Make request with invalid authToken
        EventsResult response = eventsService.getAllEvents(authToken);

        // Verify that response contains an error message
        assertEquals("Error: Invalid AuthToken", response.getMessage());
    }
}
