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
public class PersonsService extends Service {
    /**
     * Returns the result of a request to retrieve person data for the current user.
     * @return PersonResult object containing either an array of Person objects or an error message.
     */
    public PersonsResult getAllPersons(String authToken) {
        Database db = new Database();
        PersonsResult personsResult = new PersonsResult();

        try {
            db.openConnection();

            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());

            if(authTokenDao.authTokenExists(authToken)) {
                String userName = authTokenDao.authenticateString(authToken).getUsername();
                personsResult.setData(personDao.getPersonsForUsername(userName));

                handleResponse(db, personsResult, "GetAllPersons Succeeded.", true);
            } else {
                handleResponse(db, personsResult, "Error: Invalid auth token", false);
            }
        } catch(DataAccessException e) {
            handleResponse(db, personsResult, "Error: Internal Server", false);
        }
        return personsResult;
    }
}
