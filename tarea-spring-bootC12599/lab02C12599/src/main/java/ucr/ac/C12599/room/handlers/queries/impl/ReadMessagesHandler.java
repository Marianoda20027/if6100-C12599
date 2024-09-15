package ucr.ac.C12599.room.handlers.queries.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucr.ac.C12599.room.jpa.entities.MessageEntity;
import ucr.ac.C12599.room.jpa.entities.RoomEntity;
import ucr.ac.C12599.room.jpa.repositories.MessageRepository;
import ucr.ac.C12599.room.jpa.repositories.RoomRepository;

import java.util.List;
import java.util.Optional;

@Component
public class ReadMessagesHandler {

    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public ReadMessagesHandler(RoomRepository roomRepository, MessageRepository messageRepository) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
    }

    public List<MessageEntity> getMessages(String roomId) {
        if (roomId == null || roomId.isEmpty()) {
            throw new IllegalArgumentException("roomId no puede estar vacío.");
        }

        Optional<RoomEntity> roomOpt = roomRepository.findByRoomIdentifier(roomId);
        if (!roomOpt.isPresent()) {
            throw new IllegalArgumentException("La sala con el ID proporcionado no existe.");
        }

        RoomEntity room = roomOpt.get();
        return messageRepository.findByRoomOrderByCreatedOnAsc(room);
    }
}
