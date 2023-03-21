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
public class EventIDService {
    /**
     * Retrieves an event by its ID.
     *
     * @return An EventIDResult object indicating the success or failure of the operation, as well as the retrieved event data.
     */
    EventIDResult event() {
        return null;
    }

    public EventIDResult getEventById(String eventID, String authToken) {

        EventIDResult response = new EventIDResult();


        Database db = new Database();
        try {
            db.openConnection();

            AuthTokenDao tDao = new AuthTokenDao(db.getConnection());
            EventDao eDao = new EventDao(db.getConnection());

            if (tDao.authTokenExists(authToken)) {

                String userName = tDao.authenticateString(authToken).getUsername(); // get associated username for Event

                if (eDao.eventExists(eventID)) { // check if event exists by its ID

                    Event event = eDao.find(eventID); // find event by ID
                    String eventUsername = event.getAssociatedUsername(); // user associated with event

                    if (userName.equals(eventUsername)) { // if logged user is the owner of the new event

                        response.setEventID(event.getEventID());
                        response.setAssociatedUsername(event.getAssociatedUsername());
                        response.setLatitude(event.getLatitude());
                        response.setLongitude(event.getLongitude());
                        response.setCountry(event.getCountry());
                        response.setCity(event.getCity());
                        response.setEventType(event.getEventType());
                        response.setYear(event.getYear());
                        response.setPersonID(event.getPersonID());

                        response.setSuccess(true);
                        db.closeConnection(true);
                    } else {
                        response.setSuccess(false);
                        response.setMessage("Error requesting an by user - Event By Id Service");
                        db.closeConnection(false);
                    }
                } else {
                    response.setSuccess(false);
                    response.setMessage("Error invalid eventID - Event by Id Service");
                    db.closeConnection(false);
                }
            } else {
                response.setSuccess(false);
                response.setMessage("Error - Event by Id Service");
                db.closeConnection(false);
            }
        } catch (DataAccessException e) {
            response.setSuccess(false);
            response.setMessage("Internal server error - Event by Id Service");
            db.closeConnection(false);
        }
        return response;
    }
}
