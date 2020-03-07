package services;

import dao.AuthTokenDao;
import dao.UserDao;
import models.AuthorizationToken;
import requests.LoginRequest;
import models.Users;
import results.LoginResult;

import java.sql.SQLException;

/** LoginService contains methods relating to login command, accesses DAOs, contains:
 * AuthorizationToken
 * AuthTokenDao
 * UserDao
 * User
 */
public class LoginService {

    private AuthorizationToken authToken = new AuthorizationToken();
    private AuthTokenDao authTokenDao = new AuthTokenDao();
    private UserDao userDao = new UserDao();
    private Users userToFind;

//______________________________________ Login User _________________________________________________
    /** login logs a user in after checking for validity of information passed
     * @param logReq object containing login information
     * @return LoginResult object containing either user info if successful or error message if not
     */
    public LoginResult login(LoginRequest logReq)
    {
        try {
            if (!isLoginValid(logReq)) {
                return new LoginResult("Invalid input error");
            }
            userToFind = userDao.findUser(logReq.getLoginUserName());

            if (userToFind == null) {
                return new LoginResult("Invalid user error");
            }
            else if (!userToFind.getUserPassword().equals(logReq.getLoginPassWord())) {
                return new LoginResult("Invalid password error");
            }
            else {
                authToken.setUsername(logReq.getLoginUserName());
                authTokenDao.insertToken(authToken);
                return new LoginResult(authToken.getAuthToken(), userToFind.getUserNameID(), userToFind.getUserPersonID());
            }
        }
        catch (SQLException databaseError) {
            return new LoginResult("Internal Server Error.");
        }
    }

    //---********************---- checks validity of login information ---**********************----
    private boolean isLoginValid(LoginRequest logReq)
    {
        return !(logReq.getLoginPassWord() == null || logReq.getLoginUserName() == null);
    }


}
