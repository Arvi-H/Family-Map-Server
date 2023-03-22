package Service;

import Model.User;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    private LoginService loginService;
    private RegisterService registerService;

    @BeforeEach
    public void setUp() {
        loginService = new LoginService();
        registerService = new RegisterService();
    }

    @AfterEach
    public void tearDown() {
        new ClearService().clear();
    }

    @Test
    public void loginPass() {
        User user = new User("arvih", "passWord","test@email.com", "arvi", "haxhillari","m", "12345");
        RegisterResult response = registerService.register(new RegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getGender()));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(user.getUsername());
        loginRequest.setPassword(user.getPassword());

        LoginResult loginResult = loginService.login(loginRequest);

        assertTrue(loginResult.isSuccess());
    }

    @Test
    public void loginFail() {
        User user = new User("arvih", "passWord","test@email.com", "arvi", "haxhillari","m", "12345");
        LoginRequest request = new LoginRequest();
        request.setUsername(user.getUsername());
        request.setPassword(user.getPassword());

        LoginResult response = loginService.login(request);
        assertFalse(response.isSuccess());
    }

}
