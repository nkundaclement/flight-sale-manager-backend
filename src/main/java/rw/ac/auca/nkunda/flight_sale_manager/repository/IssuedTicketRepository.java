package rw.ac.auca.nkunda.flight_sale_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.ac.auca.nkunda.flight_sale_manager.model.IssuedTicket;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssuedTicketRepository extends JpaRepository<IssuedTicket, UUID> {
    @Query("SELECT d FROM IssuedTicket d "
            + "WHERE d.salesOrder.client.passportNumber = :passportNumber "
            + "ORDER BY d.issuedDate DESC")
    List<IssuedTicket> findTicketsOfClient(@Param("passportNumber") String passportNumber);

}