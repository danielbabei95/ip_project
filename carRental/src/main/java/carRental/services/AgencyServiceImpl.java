package carRental.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carRental.models.Agency;
import carRental.repositories.AgencyRepository;

import java.util.List;

@Service
public class AgencyServiceImpl implements AgencyService {
    @Autowired
    private AgencyRepository repository;

    @Override
    public Agency save(Agency entity) {
        return this.repository.save(entity);
    }

    @Override
    public List<Agency> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Agency getById(Long id) {
        return this.repository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        this.repository.delete(id);
    }
}
