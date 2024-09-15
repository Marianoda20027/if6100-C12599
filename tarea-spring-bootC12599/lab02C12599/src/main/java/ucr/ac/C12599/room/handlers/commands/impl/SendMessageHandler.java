package ucr.ac.C12599.room.handlers.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucr.ac.C12599.room.handlers.commands.ISendMessageHandler;
import ucr.ac.C12599.room.jpa.entities.MessageEntity;
import ucr.ac.C12599.room.jpa.entities.RoomEntity;
import ucr.ac.C12599.room.jpa.entities.UserEntity;
import ucr.ac.C12599.room.jpa.repositories.MessageRepository;
import ucr.ac.C12599.room.jpa.repositories.RoomRepository;
import ucr.ac.C12599.room.jpa.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class SendMessageHandler implements ISendMessageHandler {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public SendMessageHandler(RoomRepository roomRepository, UserRepository userRepository, MessageRepository messageRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public MessageEntity handle(String roomId, String alias, String message) {
        if (roomId == null || roomId.isEmpty() || alias == null || alias.isEmpty() || message == null || message.isEmpty()) {
            throw new IllegalArgumentException("roomId, alias, o mensaje no pueden estar vacíos.");
        }

        Optional<RoomEntity> roomOpt = roomRepository.findByRoomIdentifier(roomId);
        if (!roomOpt.isPresent()) {
            throw new IllegalArgumentException("La sala con el ID proporcionado no existe.");
        }

        RoomEntity room = roomOpt.get();
        Optional<UserEntity> userOpt = userRepository.findByAliasAndRoom(alias, room);
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("El alias no está registrado en la sala.");
        }

        MessageEntity msg = new MessageEntity();
        msg.setMessage(message);
        msg.setCreatedOn(LocalDateTime.now());
        msg.setUser(userOpt.get());
        msg.setRoom(room);

        return messageRepository.save(msg);
    }
}
