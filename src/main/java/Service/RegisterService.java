package Service;

import DataAccess.*;
import Model.*;
import Request.RegisterRequest;
import Result.RegisterResult;

import static Network.RandomUUID.getRandomUUID;

/**
 * A service class that handles registration requests from the user.
 */
public class RegisterService extends Service {
    /**
     * Registers a user with the given information.
     * @param registerRequest the RegisterRequest containing the user's information.
     * @return the RegisterResult indicating whether the registration was successful or not.
     */
    public RegisterResult register(RegisterRequest registerRequest) {
        Database db = new Database();
        RegisterResult registerResult = new RegisterResult();

        try {
            db.openConnection();

            UserDao userDao = new UserDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());

            // If the request is valid
            if (validRequest(registerRequest)) {
                if (userDao.find(registerRequest.getUsername()) == null) {
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
                    registerResult.setRegisterResult(authTokenString, userToInsert.getUsername(), newPersonID);

                    // Handle Response and Close Connection
                    handleResponse(db, registerResult,"Register Succeeded.", true);

                } else {
                    handleResponse(db, registerResult,"Error: Username Already Exists", false);
                }
            } else {
                handleResponse(db, registerResult,"Error: Missing info", false);
            }
        } catch (DataAccessException e) {
            handleResponse(db, registerResult, e.getMessage(), false);
        }
        return registerResult;
    }

    public boolean validRequest(RegisterRequest registerRequest) {
        return !registerRequest.getUsername().isEmpty() && !registerRequest.getPassword().isEmpty() && !registerRequest.getEmail().isEmpty() && !registerRequest.getFirstName().isEmpty() && !registerRequest.getLastName().isEmpty() && (registerRequest.getGender().equals("m") || registerRequest.getGender().equals("f"));
    }
}
