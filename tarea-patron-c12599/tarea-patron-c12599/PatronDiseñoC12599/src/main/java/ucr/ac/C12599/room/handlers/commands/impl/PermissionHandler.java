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
        System.out.println("Checking permissions for user: " + request.getUser() + " with role: " + request.getRole());

        if (checkPermissions(request)) {
            System.out.println("Permission granted for user: " + request.getUser() + " with role: " + request.getRole());
            if (nextHandler != null) {
                nextHandler.handleRequest(request);  // Continuar con la cadena si tiene permisos
            }
        } else {
            System.out.println("Permission denied for user: " + request.getUser() + " with role: " + request.getRole());
            request.setAuthorized(false);  // Indicar que la solicitud no tiene permisos
            return;  // Detener la cadena si los permisos son denegados
        }
    }

    private boolean checkPermissions(RequestEntity request) {
        // Simular la lógica de verificación de permisos
        boolean hasPermission = "ADMIN".equals(request.getRole());
        System.out.println("Check result: " + hasPermission);  // Agregar logging para depuración
        return hasPermission;  // Solo permite rol "ADMIN"
    }
}
