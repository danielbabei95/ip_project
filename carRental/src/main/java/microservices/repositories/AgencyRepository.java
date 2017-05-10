package microservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import microservices.models.Agency;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
}
