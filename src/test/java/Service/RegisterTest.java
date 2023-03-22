package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.User;
import Request.RegisterRequest;
import Result.FillResult;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {
    private RegisterService registerService;
    private Database db;

    @BeforeEach
    public void setUp() {
        db = new Database();
        registerService = new RegisterService();
    }

    @AfterEach
    public void tearDown() {
        new ClearService().clear();
    }

    @Test
    public void registerPass() {
        User user = new User("arvih", "passWord","test@email.com", "arvi", "haxhillari","m", "12345");
        RegisterResult registerResult = registerService.register(new RegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getGender()));
        assertEquals(registerResult.getUsername(), user.getUsername());
    }

    @Test
    public void testRegisterFail() throws Exception {
        // Attempt to register a user with missing information
        RegisterRequest registerRequest = new RegisterRequest("", "", "email", "firstName", "lastName", "m");
        RegisterResult registerResult = registerService.register(registerRequest);

        assertFalse(registerResult.isSuccess());
        assertNull(registerResult.getAuth_token());
        assertNull(registerResult.getPersonID());
        assertNull(registerResult.getUsername());
        assertEquals("Error: Missing info", registerResult.getMessage());

        // Verify that the user was not inserted into the database
        try {
            db.openConnection();
            UserDao userDao = new UserDao(db.getConnection());
            assertNull(userDao.find(registerRequest.getUsername()));
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
    }
}
