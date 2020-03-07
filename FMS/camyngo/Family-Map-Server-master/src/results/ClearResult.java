package results;

/** ClearResult is the result of the clear command to the user, contains:
 * A string with either an error message or success message
 */
public class ClearResult {

    private String result;

    // ========================== Constructor ========================================
    public ClearResult(String result)
    {
        this.result = result;
    }

    //_______________________________ Getters and Setters __________________________________________
    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }
}
