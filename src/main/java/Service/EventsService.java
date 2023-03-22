package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Result.EventsResult;

/**
 * EventService class represents the service class for retrieving events for a user or all users.
 */
public class EventsService extends Service {

    /**
     * Retrieves all events for all family members of the current user.
     * @return An EventResult object that contains an array of Event objects for all family members of the current user
     */
    public EventsResult getAllEvents(String authid) {
        Database db = new Database();
        EventsResult eventsResult = new EventsResult();

        try {
            db.openConnection();

            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());

            if (authTokenDao.find(authid) != null) {
                String userName = authTokenDao.find(authid).getUsername();
                eventsResult.setData(eventDao.findAllEvents(userName));

                handleResponse(db, eventsResult, "GetAllEvents Succeeded.", true);
            } else {
                handleResponse(db, eventsResult, "Error: Invalid AuthToken", false);
            }
        } catch(DataAccessException e) {
            handleResponse(db, eventsResult, "Error: Internal server", false);
        }

        return eventsResult;
    }
}
