package com.example.login.server.Task;
import android.os.AsyncTask;
import com.example.login.server.ServerProxy;
import com.example.login.models.Model;
import com.example.login.server.result.LoginResult;
import com.example.login.server.request.LoginRequest;
/** LoginTask
 * The LoginTask extends the AsyncTask and is used to check if the login or register request is valid
 * and then uses a DataTask to extract data
 */

public class LoginTask extends AsyncTask<LoginRequest, LoginResult, LoginResult> implements DataTask.DataContext {
    private String serverHost;
    private String ipAddress;
    private LoginContext context;

    ///////// Interface //////////
    public interface LoginContext {
        void onExecuteComplete(String message);
    }

    // ========================== Constructor ========================================
    public LoginTask(String server, String ip, LoginContext c)
    {
        serverHost = server;
        ipAddress = ip;
        context = c;
    }

    //--****************-- Do In Background --***************--
    @Override
    protected LoginResult doInBackground(LoginRequest... logReqs)
    {
        ServerProxy serverProxy = ServerProxy.initialize();
        LoginResult loginResult = serverProxy.login(serverHost, ipAddress, logReqs[0]);
        return loginResult;
    }

    //--****************-- On Post Execute --***************--
    @Override
    protected void onPostExecute(LoginResult loginResult)
    {
        if (loginResult.getErrorMessage() == null){
            Model model = Model.initialize();

            model.setServerHost(serverHost);
            model.setIpAddress(ipAddress);
            model.setAuthToken(loginResult.getToken());

            DataTask dataTask = new DataTask(serverHost, ipAddress, this);
            dataTask.execute(loginResult.getToken());
        }
        else {
            context.onExecuteComplete(loginResult.getErrorMessage());
        }
    }

    //--****************-- Receive Completion from DataTask --***************--
    @Override
    public void onExecuteCompleteData(String message)
    {
        context.onExecuteComplete(message);
    }


}


