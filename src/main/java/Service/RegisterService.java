package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.RegisterRequest;
import Result.ClearResult;
import Result.RegisterResult;

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
            db.openConnection();


            db.closeConnection(true);
        } catch (DataAccessException e) {
            registerResult.setMessage(e.getMessage());
            registerResult.setSuccess(false);
            db.closeConnection(false);
        }
        return registerResult;
    }
}
