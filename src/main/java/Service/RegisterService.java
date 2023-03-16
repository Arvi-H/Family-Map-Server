package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.User;
import Request.RegisterRequest;
import Result.ClearResult;
import Result.RegisterResult;

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
        RegisterResult registerResult;

        try {
            // Open Connection
            db.openConnection();

            // If the request is valid
            if (validateRequest(registerRequest)) {
                // TODO If the user is already in the database?

                // New User
                String newPersonID = getRandomUUID();
                User userToInsert = new User(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail(), registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getGender(), newPersonID);

                // Insert New User
                new UserDao(db.getConnection()).insert(userToInsert);

                // Generate Tree for New User
                PersonDao personDao = new PersonDao(db.getConnection());
                personDao.generateTree(userToInsert, newPersonID, 4, new EventDao(db.getConnection()));

                // Generate Random AuthToken
                String authTokenString = getRandomUUID();
                AuthToken authToken = new AuthToken(authTokenString, userToInsert.getUsername());
                new AuthTokenDao(db.getConnection()).insert(authToken);

                // Record Response
                registerResult = new RegisterResult(authTokenString, userToInsert.getUsername(), newPersonID);
                registerResult.setSuccess(true);

                // Close Connection
                db.closeConnection(true);
            } else {
                registerResult = new RegisterResult();
                registerResult.setMessage("Error: invalid request");
                registerResult.setSuccess(false);
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

    public boolean validateRequest(RegisterRequest registerRequest) {
        return !(registerRequest.getUsername().isEmpty() &&
                 registerRequest.getPassword().isEmpty() &&
                 registerRequest.getEmail().isEmpty() &&
                 registerRequest.getFirstName().isEmpty() &&
                 registerRequest.getLastName().isEmpty() &&
                (registerRequest.getGender().equals("m") || registerRequest.getGender().equals("f")));
    }
}
