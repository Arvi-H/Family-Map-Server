package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.User;
import Request.RegisterRequest;
import Result.ClearResult;
import Result.RegisterResult;
import passoffrequest.FillRequest;

import static Network.RandomUUID.getRandomUUID;

/**
 * A service class that handles registration requests from the user.
 */
public class RegisterService {
    /**
     * Registers a user with the given information.
     * @param registerRequest the RegisterRequest containing the user's information.
     * @return the RegisterResult indicating whether the registration was successful or not.
     */
    public RegisterResult register(RegisterRequest registerRequest) {
        Database db = new Database();
        RegisterResult registerResult = new RegisterResult();

        try {
            // Open Connection
            db.openConnection();

            // Data Access Classes
            UserDao userDao = new UserDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());

            boolean validUsername = !registerRequest.getUsername().isEmpty();
            boolean validPassword = !registerRequest.getPassword().isEmpty();
            boolean validEmail = !registerRequest.getEmail().isEmpty();
            boolean validFirstName = !registerRequest.getFirstName().isEmpty();
            boolean validLastName = !registerRequest.getLastName().isEmpty();
            boolean validGender = (registerRequest.getGender().equals("m") || registerRequest.getGender().equals("f"));

            // If the request is valid
            if (validUsername && validPassword && validEmail && validFirstName && validLastName && validGender) {
                if (userDao.userExists(registerRequest.getUsername())) {
                    registerResult.setSuccess(false);
                    registerResult.setMessage("Error username already exists - Register Service");
                    db.closeConnection(false);
                } else {
                    // New User
                    String newPersonID = getRandomUUID();
                    User userToInsert = new User(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail(), registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getGender(), newPersonID);

                    // Insert New User
                    userDao.insert(userToInsert);

                    // Generate Tree for New User
                    personDao.generateTree(userToInsert, newPersonID, 4, eventDao);

                    // Generate Random AuthToken
                    String authTokenString = getRandomUUID();
                    AuthToken authToken = new AuthToken(authTokenString, userToInsert.getUsername());
                    authTokenDao.insert(authToken);

                    // Record Response
                    registerResult.setUsername(userToInsert.getUsername());
                    registerResult.setAuth_token(authTokenString);
                    registerResult.setPersonID(newPersonID);
                    registerResult.setSuccess(true);

                    // Close Connection
                    db.closeConnection(true);
                }
            } else {
                registerResult.setSuccess(false);
                registerResult.setMessage("Error: Missing info");
                db.closeConnection(false);
            }
        } catch (DataAccessException e) {
            registerResult = new RegisterResult();
            registerResult.setMessage(e.getMessage());
            registerResult.setSuccess(false);
            db.closeConnection(false);
        }
        return registerResult;
    }
}
