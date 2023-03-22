package Service;

import DataAccess.*;
import Model.User;
import Result.FillResult;

/**
 * Represents a service to generate data for a specified user account
 */
public class FillService {
    /**
     * Generates data for the specified user account
     * @return A FillResult object containing the result of the operation
     */
    public FillResult fill(String username, int generations) {
        Database db = new Database();
        FillResult fillResult = new FillResult();

        try {
            // Open Connection
            db.openConnection();

            // Data Access Classes
            UserDao userDao = new UserDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());

            if((userDao.find(username) != null) && generations >= 0) {
                User user = userDao.find(username);

                // Clear database
                personDao.clearPerson(username);
                eventDao.clearEvent(username);

                if(generations == 99) {
                    personDao.generateTree(user, user.getPersonID(), 4, eventDao);
                } else {
                    personDao.generateTree(user, user.getPersonID(), generations, eventDao);
                }


                int numOfPersons = personDao.getPersonCount();
                int numOfEvents = eventDao.getNumOfEvents();

                fillResult.setMessage("Successfully added " + numOfPersons + " persons and " + numOfEvents + " events to the database!");
                fillResult.setSuccess(true);
                db.closeConnection(true);

            } else {
                fillResult.setSuccess(false);
                fillResult.setMessage("Error: Invalid username.");
                db.closeConnection(false);

            }
        } catch(DataAccessException e) {

            fillResult.setSuccess(false);
            fillResult.setMessage("Internal server error - Fill Service");

            try {
                db.closeConnection(false);
            } catch (Exception j) {
                j.printStackTrace();
            }
        }

        return fillResult;
    }
}
