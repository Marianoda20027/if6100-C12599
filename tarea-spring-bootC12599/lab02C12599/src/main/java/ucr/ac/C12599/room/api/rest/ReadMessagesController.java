package ucr.ac.C12599.room.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucr.ac.C12599.room.handlers.queries.impl.ReadMessagesHandler;
import ucr.ac.C12599.room.jpa.entities.MessageEntity;

import java.util.List;

@RestController
@RequestMapping("/api/C12599/room")
public class ReadMessagesController {

    private final ReadMessagesHandler readMessagesHandler;


    public ReadMessagesController( ReadMessagesHandler readMessagesHandler) {
        this.readMessagesHandler = readMessagesHandler;

    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageEntity>> getMessages(@RequestParam String roomId) {
        List<MessageEntity> messages = readMessagesHandler.getMessages(roomId);
        if (messages == null || messages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(messages);
    }
}
