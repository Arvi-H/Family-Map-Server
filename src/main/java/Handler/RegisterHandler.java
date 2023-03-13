package Handler;

import Request.RegisterRequest;
import Result.RegisterResult;
import Service.RegisterService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class RegisterHandler extends Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (isPOSTRequest(exchange)) {
                RegisterRequest registerRequest = parseRequest(exchange, Request.RegisterRequest.class);
                RegisterService registerService = new RegisterService();
                RegisterResult registerResult = registerService.register(registerRequest);

                sendResponse(exchange, registerResult);
                parseResponse(exchange, registerResult);
            } else {
                sendBadRequestResponse(exchange);
            }
            closeExchange(exchange);
        } catch (IOException e) {
            handleIOException(exchange, e);
        }
    }
}
