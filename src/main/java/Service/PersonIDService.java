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
public class PersonIDService {
    /**
     * This method generates a unique person ID for a new user when they register on the family map server.
     * @return the PersonIDResult object containing the generated person ID and a success status flag.
     */
    PersonIDResult personID() {
        return null;
    }

    public PersonIDResult getPerson(String personID, String authtoken) throws DataAccessException {

        PersonIDResult response = new PersonIDResult();
        Database db = new Database();

        try {
            db.openConnection();

            AuthTokenDao tDao = new AuthTokenDao(db.getConnection());
            PersonDao pDao = new PersonDao(db.getConnection());

            if(tDao.authTokenExists(authtoken)) {

                String userName = tDao.authenticateString(authtoken).getUsername();

                if(pDao.find(personID) != null) {

                    Person person = pDao.find(personID);
                    String personUsername = person.getAssociatedUsername();

                    if(userName.equals(personUsername)) {
                        response.setAssociatedUsername(person.getAssociatedUsername());
                        response.setPersonID(person.getPersonID());
                        response.setFirstName(person.getFirstName());
                        response.setLastName(person.getLastName());
                        response.setGender(person.getGender());
                        if(person.getFatherID() != null) { response.setFatherID(person.getFatherID()); }
                        if(person.getMotherID() != null) { response.setMotherID(person.getMotherID()); }
                        if(person.getSpouseID() != null) { response.setSpouseID(person.getSpouseID()); }

                        response.setSuccess(true);
                        db.closeConnection(true);
                    } else {
                        response.setSuccess(false);
                        response.setMessage("Error requesting this person - Person by Id Service");
                        db.closeConnection(false);
                    }
                } else {
                    response.setSuccess(false);
                    response.setMessage("Error person ID is not valid - Person by Id Service");
                    db.closeConnection(false);
                }
            } else {
                response.setSuccess(false);
                response.setMessage("Error check your token to see if valid - Person by Id Service");
                db.closeConnection(false);
            }
        } catch(DataAccessException e) {
            response.setSuccess(false);
            response.setMessage("Internal server error - Person by Id Service");
            db.closeConnection(false);
        }

        return response;
    }
}
