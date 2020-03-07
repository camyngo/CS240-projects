package models;

import java.util.Objects;
import java.util.UUID;

/** AuthorizationToken class contains:
 *  a string that is an identifiable Authorization Token (randomly generated),
 *  the username which it is paired to
 */
public class AuthorizationToken {

    private String authToken;
    private String userName;

// ___________________________________________ Constructors ___________________________________________
    public AuthorizationToken()
    {
        authToken = UUID.randomUUID().toString();
        userName = null;
    }

    public AuthorizationToken(String name)
    {
        authToken = UUID.randomUUID().toString();
        userName = name;
    }

    public AuthorizationToken(String tokenString, String name)
    {
        authToken = tokenString;
        userName = name;
    }

    public String getAuthToken()
    {
        return authToken;
    }

    public void setAuthToken(String newAuthToken)
    {
        authToken = newAuthToken;
    }

    public String getUsername()
    {
        return userName;
    }

    public void setUsername(String newUserName)
    {
        userName = newUserName;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        AuthorizationToken that = (AuthorizationToken) o;
        return authToken.equals(that.authToken) && userName.equals(that.userName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(authToken, userName);
    }
}


