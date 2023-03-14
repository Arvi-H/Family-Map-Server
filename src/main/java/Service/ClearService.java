package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Result.ClearResult;

/**
 * This class provides a service for clearing the database.
 */
public class ClearService {
    /**
     * Clears all data from the database.
     * @return A ClearResult object indicating the success or failure of the operation.
     */
    public ClearResult clear() {
        Database db = new Database();
        ClearResult clearResult = new ClearResult();

        try {
            db.openConnection();
            db.clearTables();

            clearResult.setMessage("Cleared successfully");
            clearResult.setSuccess(true);

            db.closeConnection(true);
        } catch (DataAccessException e) {
            clearResult.setMessage(e.getMessage());
            clearResult.setSuccess(false);
            db.closeConnection(false);
        }
        return clearResult;
    }
}
