package handlers;

import com.sun.net.httpserver.*;

import java.net.*;
import java.io.*;
import com.google.gson.*;
import results.AllEventResults;
import results.SingleEventResult;
import services.EventService;

/** EventHandler handles all event commands that are requested */
public class EventHandler implements HttpHandler {

//______________________________________ Http Handler (Event) _________________________________________________
    /** handle handles (duh) the http requests and directs services
     * @param exchange http exchange object
     * @throws IOException if something goes wrong in reading files and such
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {

                    String authToken = reqHeaders.getFirst("Authorization");
                    String uRI = exchange.getRequestURI().toString();

                    String respData = directPath(uRI, authToken);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(respData, respBody);
                    respBody.close();
                    success = true;

                }
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException inputException) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            exchange.getResponseBody().close();
            inputException.printStackTrace();
        }
    }

    private String directPath(String command, String authToken)
    {
        String respData;
        EventService eventService = new EventService();
        Gson gson = new Gson();

        // this event return all related to the user
        if (command.substring(0,7).equals("/event/") && command.length() == 7){
            AllEventResults allResult = eventService.allEvents(authToken);
            if (!(allResult.getErrorMessage() == null)){
                respData = "{ \"message\": \"" + allResult.getErrorMessage() + "\"}";
            }
            else {
                respData = gson.toJson(allResult);
            }
        }

        // this event will return a single person
        else if (command.substring(0,7).equals("/event/") && command.length() != 7){
            SingleEventResult singleResult = eventService.singleEvent(command.substring(7), authToken);
            if (!(singleResult.getErrorMessage() == null)){
                respData = "{ \"message\": \"" + singleResult.getErrorMessage() + "\"}";
            }
            else {
                respData = gson.toJson(singleResult);
            }
        }
        else {
            respData = "Internal server error";
        }

        return respData;
    }

    //---********************---- writes a string to an outputStream ---**********************----
    private void writeString(String str, OutputStream outStream) throws IOException
    {
        OutputStreamWriter streamWriter = new OutputStreamWriter(outStream);
        streamWriter.write(str);
        streamWriter.flush();
    }
}
