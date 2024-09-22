package ucr.ac.C12599.room.handlers.commands.impl;

import ucr.ac.C12599.room.handlers.RequestHandler;
import ucr.ac.C12599.room.jpa.entities.RequestEntity;

public class PermissionHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(RequestEntity request) {
        System.out.println("Checking permissions for role: " + request.getRole());

        if (checkPermissions(request)) {
            System.out.println("Permission granted for role: " + request.getRole());
            if (nextHandler != null) {
                nextHandler.handleRequest(request);
            }
        } else {
            System.out.println("Permission denied for role: " + request.getRole());
        }
    }

    private boolean checkPermissions(RequestEntity request) {
        // Simulate permission check
        return "ADMIN".equals(request.getRole());
    }
}