package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.Person;
import Result.PersonIDResult;

/**
 * The PersonIDService class is responsible for generating a unique person ID for a new user
 * when they register on the family map server.
 */
public class PersonIDService extends Service {
    /**
     * This method generates a unique person ID for a new user when they register on the family map server.
     * @return the PersonIDResult object containing the generated person ID and a success status flag.
     */
    public PersonIDResult getPersonById(String personID, String authtoken) throws DataAccessException {

        PersonIDResult personIDResult = new PersonIDResult();
        Database db = new Database();

        try {
            db.openConnection();

            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());

            if(authTokenDao.find(authtoken) != null) {
                String userName = authTokenDao.find(authtoken).getUsername();
                Person person = personDao.find(personID);

                if(person != null) {
                    if(userName.equals(person.getAssociatedUsername())) {
                        personIDResult.setPersonIDResult(person.getAssociatedUsername(), person.getPersonID(), person.getFirstName(), person.getLastName(), person.getGender());

                        if(person.getFatherID() != null) { personIDResult.setFatherID(person.getFatherID()); }
                        if(person.getMotherID() != null) { personIDResult.setMotherID(person.getMotherID()); }
                        if(person.getSpouseID() != null) { personIDResult.setSpouseID(person.getSpouseID()); }

                        handleResponse(db, personIDResult, "GetPersonById Succeeded.", true);
                    } else {
                        handleResponse(db, personIDResult, "Error: PersonId Service Failed", false);
                    }
                } else {
                    handleResponse(db, personIDResult, "Error: Invalid PersonID", false);
                }
            } else {
                handleResponse(db, personIDResult, "Error: Person by Id Service", false);
            }
        } catch(DataAccessException e) {
            handleResponse(db, personIDResult, "Error: Internal server", false);
        }

        return personIDResult;
    }
}
