package Handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ClearHandler extends Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                Headers reqHeaders = exchange.getRequestHeaders();

                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");

                    if (authToken.equals("afj232hj2332")) {
                        InputStream reqBody = exchange.getRequestBody();
                        String reqData = readData(reqBody);
                        System.out.println(reqData);

						/*
						LoginRequest request = (LoginRequest)gson.fromJson(reqData, LoginRequest.class);

						LoginService service = new LoginService();
						LoginResult result = service.login(request);

						exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
						OutputStream resBody = exchange.getResponseBody();
						gson.toJson(result, resBody);
						resBody.close();
						*/

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        exchange.getResponseBody().close();
                        success = true;
                    }
                }
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

}

