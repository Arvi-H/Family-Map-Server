package Service;

import static org.junit.jupiter.api.Assertions.*;

import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoadTest {
    private LoadService loadService;
    private LoadRequest loadRequest;

    @BeforeEach
    public void setUp() {
        loadService = new LoadService();

        // Set up test data
        User user = new User("username", "password", "email",
                "firstName", "lastName", "m", "personID");
        Person person = new Person("personID", "username", "firstName",
                "lastName", "m", "fatherID", "motherID", "spouseID");
        Event event = new Event("eventID", "username", "personID", (float) 1.0, (float) 2.0,
                "country", "city", "eventType", 2021);

        User[] users = { user };
        Person[] persons = { person };
        Event[] events = { event };

        loadRequest = new LoadRequest(users, persons, events);
    }

    @Test
    public void testLoadPass() {
        LoadResult loadResult = loadService.load(loadRequest);
        assertEquals("Successfully added 1 users, 1 persons, and 1 events to the database", loadResult.getMessage());
    }

    @Test
    public void testLoadFail() {
        User user = new User("arvih", "passWord","test@email.com", "arvi", "haxhillari","m", "12345");
        User[] users = new User[1];
        users[0] = user;

        Person person = new Person("12345", "arvih", "arvi","haxhillari", "m", "11111","222222", "123123");
        Person[] persons = new Person[1];
        persons[0] = person;

        Event event = new Event("12345", "arvih", "12345",1.0f, 2.0f, "USA","Provo", "birth", 2003);
        Event[] events = new Event[1];
        events[0] = event;

        LoadRequest invalidLoadRequest = new LoadRequest(users, persons, events);

        LoadResult loadResult = loadService.load(invalidLoadRequest);
        assertNotNull(loadResult.getMessage());
    }
}
