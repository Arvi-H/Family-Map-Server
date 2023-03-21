package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.AuthToken;
import Model.Event;
import Result.EventsResult;
import passoffresult.EventResult;

import java.util.List;

/**
 * EventService class represents the service class for retrieving events for a user or all users.
 */
public class EventsService {

    /**
     * Retrieves all events for all family members of the current user.
     * @return An EventResult object that contains an array of Event objects for all family members of the current user
     */
    public EventsResult getAllEvents(String authID) {
        EventsResult response = new EventsResult();
        Database db = new Database();

        try {
            db.openConnection();
            AuthTokenDao tDao = new AuthTokenDao(db.getConnection());
            EventDao eDao = new EventDao(db.getConnection());

            if (tDao.authTokenExists(authID)) {

                String userName = tDao.authenticateString(authID).getUsername(); // get username for associated event
                response.setData(eDao.findAllEvents(userName));

                response.setSuccess(true);
                db.closeConnection(true);

            } else {
                response.setSuccess(false);
                response.setMessage("Error encountered when finding all events - Events Service");
                db.closeConnection(false);
            }
        } catch(DataAccessException e) {
            response.setSuccess(false);
            response.setMessage("Internal server error - Events Service");
            db.closeConnection(false);
        }

        return response;
    }
}
