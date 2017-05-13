package airportmanagement.controllers;

import airportmanagement.DTO.CreatingFlightDto;
import airportmanagement.DTO.FlightDto;
import airportmanagement.models.Airport;
import airportmanagement.models.Flight;
import airportmanagement.services.AirportService;
import airportmanagement.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("airports")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @Autowired
    private AirportService airportService;
    @airportmanagement.filters.JsonFilter(keys = {"day","arrivalCity","departureCity"})
    @RequestMapping(value = "/{location}/flights", method = RequestMethod.GET)
    public ResponseEntity<List<FlightDto>> geFlightByAirport(@PathVariable("location") String location) {
        Airport airport = this.airportService.getByLocation(location);
        if (airport == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        airport.getFlights().size();
        List<Flight> flights = airport.getFlights();

        List<FlightDto> flightDtos = new ArrayList<>();
        for (Flight flight : flights) {
            FlightDto flightDto = toDto(flight);
            flightDtos.add(flightDto);
        }
        return new ResponseEntity<>(flightDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/{location}/flights", method = RequestMethod.POST)
    public ResponseEntity<FlightDto> addFlight(@PathVariable("location") String location, @RequestBody CreatingFlightDto dto) {
        Airport airport = this.airportService.getByLocation(location);
        if (airport == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Flight newFlight = toCreatingModel(dto);
        newFlight.setAirport(airport);

        Flight savedFlight = this.flightService.save(newFlight);

        return new ResponseEntity<>(toDto(savedFlight), HttpStatus.CREATED);
    }

    private FlightDto toDto(Flight flight) {
        FlightDto flightDto = new FlightDto();
        flightDto.id = flight.getId();
        flightDto.arrivalHour = flight.getArrivalHour();
        flightDto.departureHour = flight.getDepartureHour();
        flightDto.departureCity = flight.getDepartureCity();
        flightDto.company = flight.getCompany();
        flightDto.arrivalCity = flight.getArrivalCity();
        flightDto.flightNumber = flight.getFlightNumber();
        flightDto.status=flight.getStatus();
        flightDto.day=flight.getDay();

        return flightDto;
    }

    private Flight toCreatingModel(CreatingFlightDto dto) {
        Flight flight = new Flight();
        flight.setDepartureCity(dto.departureCity);
        flight.setArrivalHour(dto.arrivalHour);
        flight.setDepartureHour(dto.departureHour);
        flight.setCompany(dto.company);
        flight.setArrivalCity(dto.arrivalCity);
        flight.setFlightNumber(dto.flightNumber);
        flight.setStatus(dto.status);
        flight.setDay(dto.day);
        return flight;
    }
}

