package com.smarthostel.controller;

import com.smarthostel.entity.Room;
import com.smarthostel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return roomRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody Room room) {
        if (room.getRoomNumber() == null || room.getBlock() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Room number and block are required."));
        }
        if (room.getOccupiedBeds() > room.getCapacity()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Occupied beds cannot exceed total room capacity."));
        }
        Room saved = roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
        return roomRepository.findById(id).map(room -> {
            if (roomDetails.getRoomNumber() != null) room.setRoomNumber(roomDetails.getRoomNumber());
            if (roomDetails.getBlock() != null) room.setBlock(roomDetails.getBlock());
            if (roomDetails.getFloor() != null) room.setFloor(roomDetails.getFloor());
            if (roomDetails.getCapacity() != null) room.setCapacity(roomDetails.getCapacity());
            if (roomDetails.getOccupiedBeds() != null) {
                if (roomDetails.getOccupiedBeds() > room.getCapacity()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body((Object) Map.of("message", "Occupied beds cannot exceed capacity."));
                }
                room.setOccupiedBeds(roomDetails.getOccupiedBeds());
            }
            if (roomDetails.getRoomType() != null) room.setRoomType(roomDetails.getRoomType());
            if (roomDetails.getStatus() != null) room.setStatus(roomDetails.getStatus());
            if (roomDetails.getMonthlyRent() != null) room.setMonthlyRent(roomDetails.getMonthlyRent());

            Room updated = roomRepository.save(room);
            return ResponseEntity.ok((Object) updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        return roomRepository.findById(id).map(room -> {
            roomRepository.delete(room);
            return ResponseEntity.ok(Map.of("message", "Room deleted successfully"));
        }).orElse(ResponseEntity.notFound().build());
    }
}
