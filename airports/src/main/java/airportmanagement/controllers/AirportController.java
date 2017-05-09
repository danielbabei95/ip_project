package airportmanagement.controllers;

import airportmanagement.DTO.AirportDto;
import airportmanagement.DTO.CreatingAirportDto;
import airportmanagement.models.Airport;
import airportmanagement.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {
    @Autowired
    private AirportService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AirportDto>> get() {
        List<Airport> airports = this.service.getAll();
        if (airports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<AirportDto> result = new ArrayList<>();

        for (Airport airport : airports) {
            AirportDto dto = toDto(airport);
            result.add(dto);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AirportDto> addAirport(@RequestBody CreatingAirportDto airportDto) {
        Airport airport = toCreatingModel(airportDto);
        Airport savedAirport = this.service.save(airport);
        return new ResponseEntity<>(toDto(savedAirport), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{location}", method = RequestMethod.GET)
    public ResponseEntity<AirportDto> getAirportByLocation(@PathVariable("location")String location) {
        Airport airport = this.service.getByLocation(location);
        if (airport == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(toDto(airport), HttpStatus.OK);
    }

    private AirportDto toDto(Airport airport) {
        AirportDto dto = new AirportDto();
        dto.name = airport.getName();
        dto.location=airport.getLocation();
        dto.id = airport.getId();
        return dto;
    }

    private Airport toCreatingModel(CreatingAirportDto dto) {
        Airport airport = new Airport();
        airport.setName(dto.name);
        airport.setLocation(dto.location);
        return airport;
    }
}
