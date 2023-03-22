package Service;

import DataAccess.Database;
import Result.Result;

public class Service {
    public void handleResponseAndCloseConnection(Database db, Result result, String message, boolean success) {
        result.setMessage(message);
        result.setSuccess(success);
        db.closeConnection(success);
    }
}
