package Handler;

import Request.LoginRequest;
import Result.LoginResult;
import Service.LoginService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class LoginHandler extends Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (isPOSTRequest(exchange)) {
                LoginRequest loginRequest = parseRequest(exchange, Request.LoginRequest.class);
                LoginService loginService = new LoginService();
                LoginResult loginResult = loginService.login(loginRequest);

                sendResponse(exchange, loginResult);
                parseResponse(exchange, loginResult);
            } else {
                sendBadRequestResponse(exchange);
            }
            closeExchange(exchange);
        } catch (IOException e) {
            handleIOException(exchange, e);
        }
    }
}
