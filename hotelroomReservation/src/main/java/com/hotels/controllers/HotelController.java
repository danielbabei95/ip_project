package com.hotelsamdrooms.controllers;

import com.hotelsamdrooms.DTO.CreatingHotelDto;
import com.hotelsamdrooms.DTO.HotelDto;
import com.hotelsamdrooms.models.Hotel;
import com.hotelsamdrooms.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelService service;

    @com.hotelsamdrooms.filters.JsonFilter(keys = {"category"})
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<HotelDto>> get() {
        List<Hotel> hotels = this.service.getAll();
        if (hotels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<HotelDto> result = new ArrayList<>();

        for (Hotel hotel : hotels) {
            HotelDto dto = toDto(hotel);
            result.add(dto);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<HotelDto> addHotel(@RequestBody CreatingHotelDto hotelDto) {
        Hotel hotel = toCreatingModel(hotelDto);
        Hotel savedHotel = this.service.save(hotel);
        return new ResponseEntity<>(toDto(savedHotel), HttpStatus.CREATED);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<HotelDto> getHotelById(@PathVariable("id") Long id) {
        Hotel hotel = this.service.getById(id);
        if (hotel == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(toDto(hotel), HttpStatus.OK);
    }

    private HotelDto toDto(Hotel hotel) {
        HotelDto dto = new HotelDto();
        dto.name = hotel.getName();
        dto.address = hotel.getAddress();
        dto.id = hotel.getId();
        dto.phone = hotel.getPhone();
        dto.category = hotel.getCategory();
        dto.description = hotel.getDescription();
        dto.facilities=hotel.getFacility();
        dto.rooms=hotel.getRoom();
        return dto;
    }

    private Hotel toCreatingModel(CreatingHotelDto dto) {
        Hotel hotel = new Hotel();
        hotel.setName(dto.name);
        hotel.setAddress(dto.address);
        hotel.setPhone(dto.phone);
        hotel.setCategory(dto.category);
        hotel.setDescription(dto.description);
        hotel.setFacility(dto.facilities);
        hotel.setRoom(dto.rooms);
        return hotel;
    }



}
