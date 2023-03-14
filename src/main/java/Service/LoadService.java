package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;

/**
 * This class represents a service that loads data or resources in response to requests.
 */
public class LoadService {
    /**
     * Loads the provided data into the database.
     * @param loadRequest LoadRequest object containing the data to be loaded
     * @return LoadResult object containing information about the success or failure of the load operation
     */
    public LoadResult load(LoadRequest loadRequest) {
        Database db = new Database();
        LoadResult loadResult = new LoadResult();
        int numUsers = 0, numPersons = 0, numEvents = 0;

        try {
            db.openConnection();
            db.clearTables();

            for (User user : loadRequest.getUsers()) {
                UserDao userDao = new UserDao(db.getConnection());
                userDao.insert(user);
                numUsers++;
            }
            for (Person person : loadRequest.getPersons()) {
                PersonDao personDao = new PersonDao(db.getConnection());
                personDao.insert(person);
                numPersons++;
            }
            for (Event event : loadRequest.getEvents()) {
                EventDao eventDao = new EventDao(db.getConnection());
                eventDao.insert(event);
                numEvents++;
            }

            String resultMsg = "Successfully added " + numUsers + " users, " + numPersons + " persons, and " + numEvents + " events to the database";
            loadResult.setMessage(resultMsg);
            loadResult.setSuccess(true);

            db.closeConnection(true);
        } catch (DataAccessException e) {
            loadResult.setMessage(e.getMessage());
            loadResult.setSuccess(false);
            db.closeConnection(false);
        }
        return loadResult;
    }
}
