package Service;

import DataAccess.*;
import Model.*;
import Request.LoadRequest;
import Result.LoadResult;

/**
 * This class represents a service that loads data or resources in response to requests.
 */
public class LoadService extends Service {
    /**
     * Loads the provided data into the database.
     * @param loadRequest LoadRequest object containing the data to be loaded
     * @return LoadResult object containing information about the success or failure of the load operation
     */
    public LoadResult load(LoadRequest loadRequest) {
        Database db = new Database();
        LoadResult loadResult = new LoadResult();

        try {
            db.openConnection();
            db.clearTables();

            UserDao userDao = new UserDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());

            for (User user : loadRequest.getUsers()) {userDao.insert(user);}
            for (Person person : loadRequest.getPersons()) {personDao.insert(person);}
            for (Event event : loadRequest.getEvents()) {eventDao.insert(event);}

            String resultMsg = "Successfully added " + loadRequest.getUsers().length + " users, " + loadRequest.getPersons().length + " persons, and " + loadRequest.getEvents().length + " events to the database";
            handleResponseAndCloseConnection(db, loadResult, resultMsg, true);

        } catch (DataAccessException e) {
            handleResponseAndCloseConnection(db, loadResult, e.getMessage(), false);
        }
        return loadResult;
    }
}
