package carRental.services;

import java.util.List;

import carRental.models.Car;

public interface CarService extends CrudService<Car> {
      List<Car> getCarsByAgencyId(Long agencyId);
}
