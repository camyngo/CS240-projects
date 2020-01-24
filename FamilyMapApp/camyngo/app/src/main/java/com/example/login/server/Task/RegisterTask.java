package com.example.login.server.Task;
import android.os.AsyncTask;

import com.example.login.models.Model;
import com.example.login.server.ServerProxy;
import com.example.login.server.result.RegisterResult;
import com.example.login.server.request.RegisterRequest;

/** RegisterTask
 * The RegisterTask extends AsyncTask and is used to check validity of a register Request,
 * and then pulls information from server using a DataTask
 */

public class RegisterTask extends AsyncTask<RegisterRequest, RegisterResult, RegisterResult> implements DataTask.DataContext{

    private String serverHost;
    private String ipAddress;
    private RegisterContext context;

    ////////// Interface ///////////
    public interface RegisterContext {
        void onExecuteComplete(String message);
    }

    // ========================== Constructor ========================================
    public RegisterTask(String server, String ip, RegisterContext c)
    {
        serverHost = server;
        ipAddress = ip;
        context = c;
    }

    //--****************-- Do In Background --***************--
    @Override
    protected RegisterResult doInBackground(RegisterRequest... registerRequests)
    {
        ServerProxy serverProxy = ServerProxy.initialize();
        RegisterResult regResult = serverProxy.register(serverHost, ipAddress, registerRequests[0]);
        return regResult;
    }

    //--****************-- On Post Execute --***************--
    @Override
    protected void onPostExecute(RegisterResult registerResult)
    {

        if (registerResult.getErrorMessage() == null){
            Model model = Model.initialize();
            model.setServerHost(serverHost);
            model.setIpAddress(ipAddress);

            DataTask dataTask = new DataTask(serverHost, ipAddress, this);
            dataTask.execute(registerResult.getToken());
        }
        else {
            context.onExecuteComplete(registerResult.getErrorMessage());
        }
    }


    //--****************-- Completion from DataTask --***************--
    @Override
    public void onExecuteCompleteData(String message)
    {
        context.onExecuteComplete(message);
    }
}