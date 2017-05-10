package carRental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import carRental.models.Agency;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
}
