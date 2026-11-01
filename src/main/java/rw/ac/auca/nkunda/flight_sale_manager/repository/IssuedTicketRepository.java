package rw.ac.auca.nkunda.flight_sale_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.nkunda.flight_sale_manager.model.IssuedTicket;

import java.util.UUID;

@Repository
public interface IssuedTicketRepository extends JpaRepository<IssuedTicket, UUID> {
}