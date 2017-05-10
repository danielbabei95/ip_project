package microservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import microservices.models.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
