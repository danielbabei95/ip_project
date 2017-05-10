package com.hotels.controllers;

import com.hotels.DTO.CreatingHotelDto;
import com.hotels.DTO.HotelDto;
import com.hotels.models.Hotel;
import com.hotels.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/hotels")
public class HotelController {
    @Autowired
    private HotelService service;

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
    public ResponseEntity<HotelDto> getStudentById(@PathVariable("id") Long id) {
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
        dto.email = hotel.getEmail();
        dto.phone = hotel.getPhone();
        dto.category = hotel.getCategory();
        dto.description = hotel.getDescription();
        return dto;
    }

    private Hotel toCreatingModel(CreatingHotelDto dto) {
        Hotel hotel = new Hotel();
        hotel.setName(dto.name);
        hotel.setAddress(dto.address);
        hotel.setEmail(dto.email);
        hotel.setPhone(dto.phone);
        hotel.setCategory(dto.category);
        hotel.setDescription(dto.description);
        return hotel;
    }



}
