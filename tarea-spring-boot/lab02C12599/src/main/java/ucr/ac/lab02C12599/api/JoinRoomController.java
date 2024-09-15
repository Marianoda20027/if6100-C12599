package ucr.ac.lab02C12599.rest;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucr.ac.lab02C12599.handlers.commands.impl.JoinRoomHandler;
import ucr.ac.lab02C12599.jpa.entities.RoomEntity;
import ucr.ac.lab02C12599.jpa.entities.UserEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/C12599/room")
public class JoinRoomController {

    private final JoinRoomHandler joinRoomHandler;

    @Autowired
    public JoinRoomController(JoinRoomHandler joinRoomHandler) {
        this.joinRoomHandler = joinRoomHandler;
    }

    @PostMapping("/join")
    public ResponseEntity<Map<String, Object>> joinRoom(@RequestBody Map<String, String> payload) {
        String roomId = payload.get("id");
        String alias = payload.get("alias");
        RoomEntity room = joinRoomHandler.handle(roomId, alias);
        if (room == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", room.getRoomIdentifier());
        response.put("name", room.getName());
        response.put("users", room.getUsers().stream().map(UserEntity::getAlias).collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }
}
