package services;

import dao.DataBaseManager;
import results.ClearResult;

import java.sql.SQLException;

/** ClearService contains methods for clearing the database */
public class ClearService {

//______________________________________ Clear Service Function _________________________________________________
    /** clearDb uses DAOs to clear the whole database
     * @return ClearResult object that contains either an error message or success message
     */
    public ClearResult clearDb()
    {
        DataBaseManager dbManager = new DataBaseManager();
        try {
            if (dbManager.clearTables()) {
                return new ClearResult("Clear succeeded.");
            }
            else {
                return new ClearResult("Internal server error.");
            }
        }
        catch (SQLException databaseError) {
            return new ClearResult("Internal server error.");
        }
    }

}
