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

            if (authTokenDao.find(authToken) != null) {
                String userName = authTokenDao.find(authToken).getUsername();
                Event event = eventDao.find(eventID);

                if (event != null) {
                    if (userName.equals(event.getAssociatedUsername())) {
                        eventIDResult.setEventIDResult(event.getAssociatedUsername(), event.getEventID(), event.getPersonID(), event.getLatitude(), event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear());
                        handleResponse(db, eventIDResult, "GetEventById succeeded.", true);
                    } else {
                        handleResponse(db, eventIDResult, "Error: requesting an by user", false);
                    }
                } else {
                    handleResponse(db, eventIDResult, "Error: Invalid eventID", false);
                }
            } else {
                handleResponse(db, eventIDResult, "Error: Invalid auth token", false);
            }
        } catch (DataAccessException e) {
            handleResponse(db, eventIDResult, "Error: Internal server", false);
        }
        return eventIDResult;
    }
}
