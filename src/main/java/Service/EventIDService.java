package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.Event;
import Result.EventIDResult;

/**
 * This class provides a service for retrieving an event by its ID.
 */
public class EventIDService extends Service {
    /**
     * Retrieves an event by its ID.
     * @return An EventIDResult object indicating the success or failure of the operation, as well as the retrieved event data.
     */
    public EventIDResult getEventById(String eventID, String authToken) {
        Database db = new Database();
        EventIDResult eventIDResult = new EventIDResult();

        try {
            db.openConnection();

            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());

            if (authTokenDao.authTokenExists(authToken)) {
                String userName = authTokenDao.authenticateString(authToken).getUsername();

                if (eventDao.eventExists(eventID)) {
                    Event event = eventDao.find(eventID);

                    if (userName.equals(event.getAssociatedUsername())) {
                        eventIDResult.setEventIDResult(event.getAssociatedUsername(), event.getEventID(), event.getPersonID(), event.getLatitude(), event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear());
                        handleResponseAndCloseConnection(db, eventIDResult, "GetEventById succeeded.", true);
                    } else {
                        handleResponseAndCloseConnection(db, eventIDResult, "Error: requesting an by user", false);
                    }
                } else {
                    handleResponseAndCloseConnection(db, eventIDResult, "Error: Invalid eventID", false);
                }
            } else {
                handleResponseAndCloseConnection(db, eventIDResult, "Error: Invalid auth token", false);
            }
        } catch (DataAccessException e) {
            handleResponseAndCloseConnection(db, eventIDResult, "Error: Internal server", false);
        }
        return eventIDResult;
    }
}
