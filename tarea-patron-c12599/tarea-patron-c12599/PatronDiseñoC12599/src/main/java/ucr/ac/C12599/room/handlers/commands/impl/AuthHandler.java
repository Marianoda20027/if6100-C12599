package ucr.ac.C12599.room.handlers.commands.impl;

import ucr.ac.C12599.room.handlers.RequestHandler;
import ucr.ac.C12599.room.jpa.entities.RequestEntity;

public class AuthHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(RequestEntity request) {
        System.out.println("Processing authentication for user: " + request.getUser());

        if (authenticate(request)) {
            request.setAuthenticated(true);
            System.out.println("Authentication successful for user: " + request.getUser());
            if (nextHandler != null) {
                nextHandler.handleRequest(request);
            }
        } else {
            request.incrementFailedAttempts();
            System.out.println("Authentication failed for user: " + request.getUser());
        }
    }

    private boolean authenticate(RequestEntity request) {
        // Simulate authentication logic
        return "admin".equals(request.getUser());
    }
}
