package airportmanagement.services;

import airportmanagement.repositories.FlightRepository;
import airportmanagement.models.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {
    @Autowired
    private FlightRepository repository;

    @Override
    public Flight save(Flight entity) {
        return this.repository.save(entity);
    }

    @Override
    public List<Flight> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Flight getById(Long id) {
        return this.repository.findOne(id);
    }

    @Override
    public Flight getByName(String name) {
        return null;
    }

    @Override
    public void delete(Long id) {
        this.repository.delete(id);
    }

//    @Override
//    public List<Flight> getGradesByStudentId(Long studentId) {
//        Stream<Flight> grades = this.repository.findAll().stream();
//        return grades.filter(x -> x.getAirport().getId() == studentId).collect(Collectors.toList());
//    }
}
