package com.hotels.controllers;

import com.hotels.DTO.CreatingRoomDto;
import com.hotels.DTO.RoomDto;
import com.hotels.models.Room;
import com.hotels.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/hotels")
public class RoomController {
    @Autowired
    private RoomService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RoomDto>> get() {
        List<Room> rooms = this.service.getAll();
        if (rooms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<RoomDto> result = new ArrayList<>();

        for (Room room : rooms) {
            RoomDto dto = toDto(room);
            result.add(dto);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RoomDto> addRoom(@RequestBody CreatingRoomDto roomDto) {
        Room room = toCreatingModel(roomDto);
        Room savedRoom = this.service.save(room);
        return new ResponseEntity<>(toDto(savedRoom), HttpStatus.CREATED);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RoomDto> getStudentById(@PathVariable("id") Long id) {
        Room room = this.service.getById(id);
        if (room == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(toDto(room), HttpStatus.OK);
    }

    private RoomDto toDto(Room room) {
        RoomDto dto = new RoomDto();
        dto.id = room.getRId();
        dto.nrBeds = room.getNrBeds();
        dto.nrRooms = room.getNrRooms();
        dto.price = room.getPrice();
        dto.availability = room.getAvailability();
        return dto;
    }

    private Room toCreatingModel(CreatingRoomDto dto) {
       Room room = new Room();

        room.setNrBeds(dto. nrBeds);
        room.setNrRooms(dto.nrRooms);
        room.setPrice(dto.price);
        room.setAvailability(dto.availability);
        return room;
    }



}
