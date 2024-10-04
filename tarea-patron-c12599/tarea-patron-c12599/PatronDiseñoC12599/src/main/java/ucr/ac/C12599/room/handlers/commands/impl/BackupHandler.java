package ucr.ac.C12599.room.handlers.commands.impl;

import ucr.ac.C12599.room.handlers.RequestHandler;
import ucr.ac.C12599.room.jpa.entities.RequestEntity;


public class BackupHandler implements RequestHandler {
    private RequestHandler nextHandler;

    @Override
    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(RequestEntity request) {
        // Realizamos una copia de seguridad solo si la solicitud es crítica (Ej: Usuario "admin")
        if ("admin".equals(request.getUser())) {
            System.out.println("Haciendo copia de seguridad de la solicitud del usuario: " + request.getUser());
        }

        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }
}
