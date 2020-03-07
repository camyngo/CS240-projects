package handlers;

import com.sun.net.httpserver.*;
import results.ClearResult;
import services.ClearService;

import java.net.*;
import java.io.*;

/** ClearHandler handles the clear requests */
public class ClearHandler implements HttpHandler {

//______________________________________ Http Handler (Clear) _________________________________________________
    /** handle handles (duh) the http requests and directs services
     * @param exchange http exchange object
     * @throws IOException if something goes wrong in reading files and such
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                ClearService clearService = new ClearService();
                ClearResult clearResult = clearService.clearDb();

                String respData = "{ \"message\": \"" +clearResult.getResult() + "\"}";

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();

                success = true;

            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
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

    //___________________________________ writes a string to an outputStream___________________________________________
    private void writeString(String str, OutputStream outStream) throws IOException
    {
        OutputStreamWriter streamWriter = new OutputStreamWriter(outStream);
        streamWriter.write(str);
        streamWriter.flush();
    }

}
