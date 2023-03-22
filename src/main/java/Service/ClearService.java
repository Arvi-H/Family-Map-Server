package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Result.ClearResult;

/**
 * This class provides a service for clearing the database.
 */
public class ClearService extends Service {
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
            handleResponseAndCloseConnection(db, clearResult, "Clear succeeded.", true);
        } catch (DataAccessException e) {
            handleResponseAndCloseConnection(db, clearResult, e.getMessage(), false);
        }

        return clearResult;
    }
}
