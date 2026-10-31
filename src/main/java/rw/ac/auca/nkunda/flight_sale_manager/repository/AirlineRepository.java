package rw.ac.auca.nkunda.flight_sale_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.nkunda.flight_sale_manager.model.Airline;

import java.util.UUID;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, UUID> {
}