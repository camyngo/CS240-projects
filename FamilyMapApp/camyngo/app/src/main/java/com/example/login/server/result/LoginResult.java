package com.example.login.server.result;

public class LoginResult {
    private String authToken;
    private String userName;
    private String personID;
    private String Message;

    // ========================== Constructors ========================================
    public LoginResult(String token, String userName, String personID)
    {
        this.authToken = token;
        this.userName = userName;
        this.personID = personID;
        this.Message = null;
    }

    public LoginResult(String message)
    {
        authToken = null;
        userName = null;
        personID = null;
        Message = message;
    }

    //_______________________________ Getters and Setters __________________________________________
    public String getToken()
    {
        return authToken;
    }

    public void setToken(String token)
    {
        this.authToken = token;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPersonID()
    {
        return personID;
    }

    public void setPersonID(String personID)
    {
        this.personID = personID;
    }

    public String getErrorMessage()
    {
        return Message;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.Message = errorMessage;
    }
}
