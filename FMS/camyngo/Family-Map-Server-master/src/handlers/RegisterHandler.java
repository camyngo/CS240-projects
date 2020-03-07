package handlers;

import com.sun.net.httpserver.*;
import java.net.*;
import java.io.*;
import com.google.gson.*;
import requests.RegisterRequest;
import results.FillResult;
import results.RegisterResult;
import services.FillService;
import services.RegisterService;

/** RegisterHandler handles register requests, contains
 * InStreamToString object to change an InputStream to a string
 */
public class RegisterHandler implements HttpHandler {

    private InStreamToString inStreamToString = new InStreamToString();

//______________________________________ Http Handler (Register) _________________________________________________
    /** handle handles (duh) the http requests and directs services
     * @param exchange http exchange object
     * @throws IOException if something goes wrong in reading files and such
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;

        try {
            Gson gson = new Gson();
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                InputStream bodyInput = exchange.getRequestBody();
                String body = inStreamToString.convertStreamToString(bodyInput);

                RegisterRequest regReq = gson.fromJson(body, RegisterRequest.class);
                JsonObject json = gson.fromJson(body, JsonObject.class);

                RegisterService service = new RegisterService();
                RegisterResult regResult = service.registerNewUser(regReq);

                //CALLING THE FILL CLASS RIGHT AFTER REGISTER
                FillService fillUser = new FillService();
                fillUser.fill(regReq.getUserNameID(),4);

                String respData = gson.toJson(regResult);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);


                respBody.close();

                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                exchange.getResponseBody().close();
                e.printStackTrace();
                }
    }

    //_____________________________________ writes a string to an outputStream _____________________________________
    private void writeString(String str, OutputStream os) throws IOException
    {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
