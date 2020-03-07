package results;

/** LoadResult is the result of /load command to the user, contains either:
 * A string that is an error message or a success message
 */
public class LoadResult {

    private String message;

    // ========================== Constructor ========================================
    public LoadResult(String result)
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
