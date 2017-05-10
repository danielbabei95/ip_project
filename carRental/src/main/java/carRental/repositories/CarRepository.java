package carRental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import carRental.models.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
