package com.example.myapplication;

import com.example.login.server.request.LoginRequest;
import com.example.login.server.request.RegisterRequest;
import com.example.login.server.result.AllEventResults;
import com.example.login.server.result.AllPersonResults;
import com.example.login.server.result.LoginResult;
import com.example.login.server.result.RegisterResult;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class Server {
    @Before
    public void setUp()
    {
        com.example.login.server.ServerProxy serverProxy = com.example.login.server.ServerProxy.initialize();
        RegisterRequest regReq = new RegisterRequest("test","nope","no","sam","hopkins", "m");
        RegisterResult registerResult = serverProxy.register("127.0.0.1", "8080", regReq);
    }

    @Test
    public void loginSucceed()
    {
        com.example.login.server.ServerProxy serverProxy = com.example.login.server.ServerProxy.initialize();
        LoginRequest loginRequest = new LoginRequest("test", "nope");
        LoginResult loginResult = serverProxy.login("127.0.0.1", "8080", loginRequest);
        // testing to make sure they getting the right user name
        assertEquals(loginResult.getUserName(),"test");
    }

    @Test
    public void loginFailUserNotFound()
    {
        com.example.login.server.ServerProxy serverProxy = com.example.login.server.ServerProxy.initialize();
        LoginRequest loginRequest = new LoginRequest("yes", "nope");
        LoginResult loginResult = serverProxy.login("127.0.0.1", "8080", loginRequest);
        // testing to make sure they getting the wrong user name with set up
        assertNotEquals(loginResult.getUserName(),"test");
    }

    @Test
    public void registerSucceed()
    {
        com.example.login.server.ServerProxy serverProxy = com.example.login.server.ServerProxy.initialize();
        RegisterRequest regReq = new RegisterRequest("cho","123","no","camy","ngo", "f");
        RegisterResult registerResult = serverProxy.register("127.0.0.1", "8080", regReq);
        // register successful => getting correct user name and token is not null
        assertEquals(registerResult.getUserName(), "cho");
        assertNotEquals(registerResult.getToken(), null);
    }

    @Test
    public void registerFailUserAlreadyExists()
    {
        com.example.login.server.ServerProxy serverProxy = com.example.login.server.ServerProxy.initialize();
        RegisterRequest regReq = new RegisterRequest("test","nope","no","sam","hopkins", "m");
        RegisterResult registerResult = serverProxy.register("127.0.0.1", "8080", regReq);
        // register unsuccessful => already taken when setting up
        assertEquals(registerResult.getErrorMessage(), "Username already taken by another user error");
    }

    @Test
    public void getAllPeopleSuccess()
    {
        com.example.login.server.ServerProxy serverProxy = com.example.login.server.ServerProxy.initialize();
        LoginRequest loginRequest = new LoginRequest("test", "nope");
        LoginResult loginResult = serverProxy.login("127.0.0.1", "8080", loginRequest);
        String authTokenTest = loginResult.getToken();
        AllPersonResults allPersonResults = serverProxy.getAllPeople("127.0.0.1", "8080", authTokenTest);
        // Authentic Token are not null
        assertNotNull(allPersonResults.getPersonsArray());
    }

    @Test
    public void getAllPeopleFail()
    {
        com.example.login.server.ServerProxy serverProxy = com.example.login.server.ServerProxy.initialize();
        String authTokenTest = "This Token Will Not Work";
        AllPersonResults allPersonResults = serverProxy.getAllPeople("127.0.0.1", "8080", authTokenTest);
        // Authentic Token are invalid
        assertEquals(allPersonResults.getErrorMessage(), "Invalid auth token error");
    }

    @Test
    public void getAllEventsSuccess()
    {
        com.example.login.server.ServerProxy serverProxy = com.example.login.server.ServerProxy.initialize();
        LoginRequest loginRequest = new LoginRequest("test", "nope");
        LoginResult loginResult = serverProxy.login("127.0.0.1", "8080", loginRequest);
        String authTokenTest = loginResult.getToken();
        AllEventResults allEventsResults = serverProxy.getAllEvents("127.0.0.1", "8080", authTokenTest);
        assertNotNull(allEventsResults.getEventsArray());
    }

    @Test
    public void getAllEventsFailUserInvalidAuthtoken()
    {
        com.example.login.server.ServerProxy serverProxy = com.example.login.server.ServerProxy.initialize();
        String authTokenTest = "this Authtoken is invalid";
        AllEventResults allEventsResults = serverProxy.getAllEvents("127.0.0.1", "8080", authTokenTest);
        // Authentic Token are invalid
        assertEquals(allEventsResults.getErrorMessage(), "Invalid auth token error");
    }
}
