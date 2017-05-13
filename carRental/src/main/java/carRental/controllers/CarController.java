package carRental.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import carRental.DTO.CarDto;
import carRental.DTO.CreatingCarDto;
import carRental.models.Agency;
import carRental.models.Car;
import carRental.services.AgencyService;
import carRental.services.CarService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private AgencyService agencyService;

    @carRental.filters.JsonFilter(keys = {"type","pricePerDay","id"})
    @RequestMapping(value = "/{agencyId}/cars", method = RequestMethod.GET)
    public ResponseEntity<List<CarDto>> getCarsByAgencyId(@PathVariable("agencyId") Long agencyId) {
        Agency agency = this.agencyService.getById(agencyId);
        if (agency == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        agency.getCars().size();
        List<Car> cars = agency.getCars();
        cars.size();

        List<CarDto> carDtos = new ArrayList<>();
        for (Car car : cars) {
            CarDto carDto = toDto(car);
            carDtos.add(carDto);
        }
        return new ResponseEntity<>(carDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/{agencyId}/cars", method = RequestMethod.POST)
    public ResponseEntity<CarDto> addCar(@PathVariable("agencyId") Long agencyId, @RequestBody CreatingCarDto dto) {
        Agency agency = this.agencyService.getById(agencyId);
        if (agency == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Car newCar = toCreatingModel(dto);
        newCar.setAgency(agency);

        Car savedCar = this.carService.save(newCar);

        return new ResponseEntity<>(toDto(savedCar), HttpStatus.CREATED);
    }

    private CarDto toDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.id = car.getId();
        carDto.type = car.getType();
        carDto.model = car.getModel();
        carDto.numberOfSeats = car.getNumberOfSeats();
        carDto.pricePerDay = car.getPricePerDay();
        carDto.availability = car.getAvailability();
        return carDto;
    }

    private Car toCreatingModel(CreatingCarDto dto) {
        Car car = new Car();
        car.setType(dto.type);
        car.setModel(dto.model);
        car.setNumberOfSeats(dto.numberOfSeats);
        car.setPricePerDay(dto.pricePerDay);
        car.setAvailability(dto.availability);
        return car;
    }
}

