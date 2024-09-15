package ucr.ac.C12599.room.handlers.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucr.ac.C12599.room.handlers.commands.IJoinRoomHandler;
import ucr.ac.C12599.room.jpa.entities.RoomEntity;
import ucr.ac.C12599.room.jpa.entities.UserEntity;
import ucr.ac.C12599.room.jpa.repositories.RoomRepository;
import ucr.ac.C12599.room.jpa.repositories.UserRepository;

import java.util.Optional;

@Component
public class JoinRoomHandler implements IJoinRoomHandler {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Autowired
    public JoinRoomHandler(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RoomEntity handle(String roomId, String alias) {
        Optional<RoomEntity> roomOpt = roomRepository.findByRoomIdentifier(roomId);
        if (!roomOpt.isPresent() || alias == null || alias.trim().isEmpty()) {
            return null;
        }

        RoomEntity room = roomOpt.get();

        if (userRepository.findByAliasAndRoom(alias, room).isPresent()) {
            return null;
        }

        UserEntity user = new UserEntity();
        user.setAlias(alias);
        user.setRoom(room);

        userRepository.save(user);

        return room;
    }
}
