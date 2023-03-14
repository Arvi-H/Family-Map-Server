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
public class LoginService {
    /**
     * Takes a LoginRequest object as input and returns a LoginResult object.
     * @param loginRequest the LoginRequest object containing the user's login information
     * @return a LoginResult object containing the authentication token, username, person ID, success status, and message
     */
    public LoginResult login(LoginRequest loginRequest) {
        Database db = new Database();
        LoginResult loginResult;

        try {
            db.openConnection();

            // Data Access classes
            UserDao userDao = new UserDao(db.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());

            // Login and password from request
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            // User that matches the username given
            User user = userDao.find(username);

            // If the user exists
            if (user != null && password.equals(user.getPassword())) {
                // Generate a new authToken and add it to the database
                String newAuthToken = getRandomUUID();
                AuthToken authToken = new AuthToken(newAuthToken, user.getUsername());
                authTokenDao.insert(authToken);

                // Create a result from this service
                loginResult = new LoginResult(newAuthToken, username, user.getPersonID(), true);

                // Commit Changes
                db.closeConnection(true);
            } else {
                // Create a result from this service
                loginResult = new LoginResult();
                loginResult.setMessage("Invalid username or password");
                loginResult.setSuccess(false);

                // Rollback Changes
                db.closeConnection(false);
            }

        } catch(DataAccessException e) {
            // Create a result from this service
            loginResult = new LoginResult();
            loginResult.setMessage("Invalid username or password");
            loginResult.setSuccess(false);

            // Rollback Changes
            db.closeConnection(false);
        }
        return loginResult;
    }
}
