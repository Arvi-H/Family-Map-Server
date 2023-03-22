package Service;

import Model.User;
import Request.RegisterRequest;
import Result.FillResult;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FillTest {
    private RegisterService registerService;
    private FillService fillService;

    @BeforeEach
    public void setUp() {
        fillService = new FillService();
        registerService = new RegisterService();
    }

    @AfterEach
    public void tearDown() {
        new ClearService().clear();
    }

    @Test
    public void fillPass() {
        User user = new User("arvih", "passWord","test@email.com", "arvi", "haxhillari","m", "12345");
        RegisterResult registerResult = registerService.register(new RegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getGender()));

        FillResult response1 = fillService.fill(user.getUsername(), 0);
        assertEquals(response1.getMessage(), "Successfully added 1 persons and 1 events to the database!");
    }

    @Test
    public void fillFail() {
        FillResult response = fillService.fill("nonexistent_user", 0);
        assertEquals(response.getMessage(), "Error: Invalid username.");
    }
}
