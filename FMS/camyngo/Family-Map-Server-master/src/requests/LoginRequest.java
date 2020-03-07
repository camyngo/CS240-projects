
package requests;

/** Login Request is a request for an authToken from an existing user, contains:
 * Username
 * Password
 */
public class LoginRequest {

    private String userName;
    private String password;

    // ___________________________________________ Default Constructors ___________________________________________
    public LoginRequest()
    {
        userName = null;
        password = null;
    }

    public LoginRequest(String loginUserName, String loginPassWord)
    {
        this.userName = loginUserName;
        this.password = loginPassWord;
    }

    //_______________________________ Getters and Setters __________________________________________
    public String getLoginUserName() {
        return userName;
    }

    public void setLoginUserName(String loginUserName) {
        this.userName = loginUserName;
    }

    public String getLoginPassWord() {
        return password;
    }

    public void setLoginPassWord(String loginPassWord) {
        this.password = loginPassWord;
    }
}
