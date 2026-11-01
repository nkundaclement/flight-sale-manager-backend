package rw.ac.auca.nkunda.flight_sale_manager.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.nkunda.flight_sale_manager.model.Airline;
import rw.ac.auca.nkunda.flight_sale_manager.repository.AirlineRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AirlineService {

    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    public String saveAirline(Airline airline) {
        airlineRepository.save(airline);
        return "Airline saved successfully";
    }

    public List<Airline> getAllAirlines() {
        return airlineRepository.findAll();
    }

    public Optional<Airline> getAirlineById(UUID id) {
        return airlineRepository.findById(id);
    }

    public String updateAirline(UUID id, Airline updated) {
        Optional<Airline> found = airlineRepository.findById(id);
        if (found.isEmpty()) {
            return "The airline with that id does not exist";
        }
        Airline airline = found.get();
        airline.setName(updated.getName());
        airlineRepository.save(airline);
        return "Airline updated successfully";
    }

    public String deleteAirline(UUID id) {
        if (!airlineRepository.existsById(id)) {
            return "The airline with that id does not exist";
        }
        airlineRepository.deleteById(id);
        return "Airline deleted successfully";
    }

    public List<Airline> getUnusedAirlines() {
        return airlineRepository.findUnusedAirlines();
    }
}