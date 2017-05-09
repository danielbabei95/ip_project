package airportmanagement.services;

import airportmanagement.repositories.AirportRepository;
import airportmanagement.models.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {
    @Autowired
    private AirportRepository repository;

    @Override
    public Airport save(Airport entity) {
        return this.repository.save(entity);
    }

    @Override
    public List<Airport> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Airport getById(Long id) {
        return this.repository.findOne(id);
    }
    @Override
    public Airport getByName(String name) {
        List<Airport> airports = this.repository.findAll();
        for (Airport airport : airports) {
            if(airport.getName().equals(name)){
                return airport;
            }
        }
     return null;
    }

    @Override
    public void delete(Long id) {
        this.repository.delete(id);
    }

    @Override
    public Airport getByLocation(String location) {
        List<Airport> airports = this.repository.findAll();
        for (Airport airport : airports) {
            if(airport.getLocation().equals(location)){
                return airport;
            }
        }
        return null;
    }
}
