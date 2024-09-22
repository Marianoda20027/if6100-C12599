package ucr.ac.C12599.room.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucr.ac.C12599.room.handlers.RequestHandler;
import ucr.ac.C12599.room.handlers.commands.impl.*;
import ucr.ac.C12599.room.jpa.dto.RequestDto;
import ucr.ac.C12599.room.jpa.entities.RequestEntity;


@RestController
@RequestMapping("/C12599/api/request")
public class RequestController {

    private final RequestHandler chain;

    public RequestController() {
        // Set up the chain of responsibility
        RequestHandler authHandler = new AuthHandler();
        RequestHandler permissionHandler = new PermissionHandler();
        RequestHandler dataSanitizationHandler = new DataSanitizationHandler();
        RequestHandler rateLimitHandler = new RateLimitHandler();
        RequestHandler cachingHandler = new CachingHandler();

        // Define the chain order
        authHandler.setNextHandler(permissionHandler);
        permissionHandler.setNextHandler(dataSanitizationHandler);
        dataSanitizationHandler.setNextHandler(rateLimitHandler);
        rateLimitHandler.setNextHandler(cachingHandler);

        this.chain = authHandler;  // First handler in the chain
    }

    @PostMapping
    public ResponseEntity<String> handleRequest(@RequestBody RequestDto requestDto) {
        // Convert DTO to Request object
        RequestEntity request = new RequestEntity(requestDto.getUser(), requestDto.getRole(), requestDto.getData());

        // Start the chain
        chain.handleRequest(request);

        // Check request status after processing by chain
        if (!request.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed.");
        }

        if (request.isCached()) {
            return ResponseEntity.status(HttpStatus.OK).body("Cached response: " + request.getData());
        }

        return ResponseEntity.status(HttpStatus.OK).body("Request processed: " + request.getData());
    }
}
