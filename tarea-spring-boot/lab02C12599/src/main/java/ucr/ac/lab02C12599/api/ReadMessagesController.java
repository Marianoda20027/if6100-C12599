package ucr.ac.lab02C12599.rest;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucr.ac.lab02C12599.handlers.queries.impl.ReadMessagesHandler;
import ucr.ac.lab02C12599.jpa.entities.RoomEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/C12599/room")
public class ReadMessagesController {

    private final ReadMessagesHandler readMessagesHandler;

    @Autowired
    public ReadMessagesController(ReadMessagesHandler readMessagesHandler) {
        this.readMessagesHandler = readMessagesHandler;
    }

    @GetMapping("/message")
    public ResponseEntity<Map<String, Object>> readMessages(@RequestBody Map<String, String> payload) {
        String roomId = payload.get("id");
        RoomEntity room = readMessagesHandler.handle(roomId);
        if (room == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", room.getRoomIdentifier());
        response.put("name", room.getName());
        response.put("messages", room.getMessages().stream().map(msg -> {
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("alias", msg.getUser().getAlias());
            messageData.put("mensaje", msg.getMessage());
            messageData.put("createdOn", msg.getCreatedOn().toString());
            return messageData;
        }).collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }
}
