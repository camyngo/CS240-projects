package results;

/** FillResult contains the result of the /fill command, either
 * a string that contains either an error message or success message
 */
public class FillResult {

    private String message;

    // ========================== Constructor ========================================
    public FillResult(String result)
    {
        this.message = result;
    }

    //_______________________________ Getters and Setters __________________________________________
    public String getResult()
    {
        return message;
    }

    public void setResult(String result)
    {
        this.message = result;
    }
}
