package airportmanagement.services;

import airportmanagement.models.Airport;

public interface AirportService extends CrudService<Airport> {
    Airport getByLocation(String location);

}
