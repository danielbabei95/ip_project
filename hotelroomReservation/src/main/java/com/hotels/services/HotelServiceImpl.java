package com.hotelsamdrooms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hotelsamdrooms.models.Hotel;
import com.hotelsamdrooms.repositories.HotelRepository;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository repository;

    @Override
    public Hotel save(Hotel entity) {
        return this.repository.save(entity);
    }

    @Override
    public List<Hotel> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Hotel getById(Long id) {
        return this.repository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        this.repository.delete(id);
    }
}
