package microservices.services;

import java.util.List;

import microservices.models.Car;

public interface CarService extends CrudService<Car> {
      List<Car> getCarsByAgencyId(Long agencyId);
}
