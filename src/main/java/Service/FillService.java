package Service;

import DataAccess.*;
import Model.User;
import Result.FillResult;

/**
 * Represents a service to generate data for a specified user account
 */
public class FillService extends Service {
    /**
     * Generates data for the specified user account
     * @return A FillResult object containing the result of the operation
     */
    public FillResult fill(String username, int generations) {
        Database db = new Database();
        FillResult fillResult = new FillResult();

        try {
            db.openConnection();

            UserDao userDao = new UserDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());

            if((userDao.find(username) != null) && generations >= 0) {
                User user = userDao.find(username);

                personDao.clearPerson(username);
                eventDao.clearEvent(username);

                personDao.generateTree(user, user.getPersonID(), (generations == 99 ? 4 : generations), eventDao);

                handleResponse(db, fillResult, "Successfully added " + personDao.getPersonCount() + " persons and " + eventDao.getNumOfEvents() + " events to the database!", true);
            } else {
                handleResponse(db, fillResult, "Error: Invalid username.", false);
            }
        } catch(DataAccessException e) {
            handleResponse(db, fillResult, "Error: Internal server", false);
        }

        return fillResult;
    }
}
