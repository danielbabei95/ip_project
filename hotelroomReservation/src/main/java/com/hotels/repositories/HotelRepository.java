package com.hotels.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hotels.models.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
