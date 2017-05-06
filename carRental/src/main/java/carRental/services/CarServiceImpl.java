package carRental.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carRental.models.Car;
import carRental.repositories.CarRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository repository;

    @Override
    public Car save(Car entity) {
        return this.repository.save(entity);
    }

    @Override
    public List<Car> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Car getById(Long id) {
        return this.repository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        this.repository.delete(id);
    }

    @Override
    public List<Car> getCarsByAgencyId(Long agencyId) {
        Stream<Car> cars = this.repository.findAll().stream();
        return cars.filter(x -> x.getAgency().getId() == agencyId).collect(Collectors.toList());
    }
}
