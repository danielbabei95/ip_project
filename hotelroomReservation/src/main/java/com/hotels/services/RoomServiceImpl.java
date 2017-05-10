package com.hotels.services;

import com.hotels.models.Room;
import com.hotels.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository repository;

    @Override
    public Room save(Room entity) {
        return this.repository.save(entity);
    }

    @Override
    public List<Room> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Room getById(Long id) {
        return this.repository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        this.repository.delete(id);
    }
}
