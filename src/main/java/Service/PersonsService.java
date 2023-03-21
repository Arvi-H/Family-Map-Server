package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Result.PersonsResult;

/**
 * The PersonService class handles retrieving information about people from
 * the database and returning it in a PersonResult object.
 */
public class PersonsService {
    /**
     * Returns the result of a request to retrieve person data for the current user.
     * @return PersonResult object containing either an array of Person objects or an error message.
     */
    PersonsResult person() {
        return null;
    }

    public PersonsResult getPersons(String authToken) {

        PersonsResult response = new PersonsResult();
        Database db = new Database();
        try {
            db.openConnection();
            AuthTokenDao tDao = new AuthTokenDao(db.getConnection());
            PersonDao pDao = new PersonDao(db.getConnection());

            if(tDao.authTokenExists(authToken)) {

                String userName = tDao.authenticateString(authToken).getUsername();
                response.setData(pDao.getPersonsForUsername(userName));

                response.setSuccess(true);
                db.closeConnection(true);
            } else {
                response.setSuccess(false);
                response.setMessage("Error: Invalid auth token");
                db.closeConnection(false);
            }
        } catch(DataAccessException e) {
            response.setSuccess(false);
            response.setMessage("Internal server error");
            db.closeConnection(false);
        }
        return response;
    }
}
