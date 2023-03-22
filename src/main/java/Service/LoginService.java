package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;

import static Network.RandomUUID.getRandomUUID;

/**
 * The LoginService class is responsible for handling login requests and generating login results.
 */
public class LoginService extends Service {
    /**
     * Takes a LoginRequest object as input and returns a LoginResult object.
     * @param loginRequest the LoginRequest object containing the user's login information
     * @return a LoginResult object containing the authentication token, username, person ID, success status, and message
     */
    public LoginResult login(LoginRequest loginRequest) {
        Database db = new Database();
        LoginResult loginResult = new LoginResult();

        try {
            db.openConnection();

            UserDao userDao = new UserDao(db.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());

            User user = userDao.find(loginRequest.getUsername());

            if (user != null && loginRequest.getPassword().equals(user.getPassword())) {
                String authTokenString = getRandomUUID();
                AuthToken authToken = new AuthToken(authTokenString, user.getUsername());

                authTokenDao.insert(authToken);
                loginResult.setLoginResult(authTokenString, loginRequest.getUsername(), user.getPersonID());

                handleResponse(db, loginResult, "Login Succeeded.", true);
            } else {
                handleResponse(db, loginResult, "Error: Invalid username or password", false);
            }
        } catch(DataAccessException e) {
            handleResponse(db, loginResult, "Error: Invalid username or password", false);
        }
        return loginResult;
    }
}
