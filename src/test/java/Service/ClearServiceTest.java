package Service;

import DataAccess.DataAccessException;
import Model.User;
import Request.RegisterRequest;
import Result.ClearResult;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearServiceTest {

    private ClearService clearService;
    private RegisterService registerService;

    @BeforeEach
    void setup() throws DataAccessException {
        registerService = new RegisterService();
        clearService = new ClearService();
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        clearService.clear();
    }

    @Test
    void clearTest() {
        clearService = new ClearService();
        ClearResult res = clearService.clear();
        assertEquals("Clear succeeded.", res.getMessage());
    }

    @Test
    void clearTest2() throws DataAccessException {
        User user = new User("arvih", "passWord","test@email.com", "arvi", "haxhillari","m", "12345");
        registerService.register(new RegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getGender()));

        clearService = new ClearService();
        ClearResult res = clearService.clear();

        assertEquals("Clear succeeded.", res.getMessage());
    }
}
