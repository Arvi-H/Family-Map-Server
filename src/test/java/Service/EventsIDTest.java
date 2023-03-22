package Service;

import DataAccess.DataAccessException;
import Model.Event;
import Model.User;
import Request.RegisterRequest;
import Result.EventIDResult;
import Result.EventsResult;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventsIDTest {
    private EventsService eventsService;
    private FillService fillService;
    private EventIDService eventIDService;
    private RegisterService registerService;

    @BeforeEach
    void setUp() throws DataAccessException {
        eventIDService = new EventIDService();
        eventsService = new EventsService();
        fillService = new FillService();
        registerService = new RegisterService();
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        new ClearService().clear();
    }

    @Test
    void testGetEventByIdSuccess() {
        User user = new User("arvih", "passWord","test@email.com", "arvi", "haxhillari","m", "12345");
        RegisterResult response = registerService.register(new RegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getGender()));

        String authToken = response.getAuth_token();
        EventsResult responseAll = eventsService.getAllEvents(authToken);
        Event[] events = responseAll.getData();

        Random num = new Random();
        String randomID = events[num.nextInt(30)].getEventID();

        EventIDResult responseID = eventIDService.getEventById(randomID, authToken);

        assertEquals(responseID.getEventID(), randomID);
    }

    @Test
    void testGetEventByIdInvalidAuthToken() throws DataAccessException, SQLException {
        // Register a new user
        User user = new User("johndoe", "password", "johndoe@example.com", "John", "Doe", "m", "12345");
        RegisterResult registerResult = registerService.register(new RegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getGender()));
        String authToken = registerResult.getAuth_token();

        // Retrieve a random event ID
        EventsResult eventsResult = eventsService.getAllEvents(authToken);
        Event[] events = eventsResult.getData();
        Random random = new Random();
        String randomID = events[random.nextInt(events.length)].getEventID();

        // Try to retrieve the event with an invalid auth token
        EventIDResult eventIDResult = eventIDService.getEventById(randomID, "invalidAuthToken");

        // Verify that the result is an error message indicating that the auth token is invalid
        assertEquals("Error: Invalid auth token", eventIDResult.getMessage());
    }

}
