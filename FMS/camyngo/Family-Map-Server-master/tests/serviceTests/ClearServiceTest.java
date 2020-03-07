package serviceTests;

import org.junit.Assert;
import org.junit.Test;
import results.ClearResult;
import services.ClearService;

public class ClearServiceTest {

    @Test
    public void clearDbTest() //Clearing the database with service method
    {
        ClearService clearService = new ClearService();
        ClearResult clearResult = clearService.clearDb();

        Assert.assertTrue(clearResult.getResult().equals("Clear succeeded."));
    }
    @Test
    public void clearDbTestFail(){
        clearDbTest();
        ClearService clearService = new ClearService();
        if(clearService!= null) System.out.println("Clear Test Fail error");
    }
}