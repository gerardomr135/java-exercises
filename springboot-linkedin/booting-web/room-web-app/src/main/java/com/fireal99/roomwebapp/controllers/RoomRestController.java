package com.fireal99.roomwebapp.controllers;

import com.fireal99.roomwebapp.models.Room;
import com.fireal99.roomwebapp.service.RoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomRestController {
    private final RoomService roomService;
    public RoomRestController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();

        return rooms;
    }
}
