package rw.ac.auca.nkunda.flight_sale_manager.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rw.ac.auca.nkunda.flight_sale_manager.model.IssuedTicket;
import rw.ac.auca.nkunda.flight_sale_manager.model.SalesOrder;
import rw.ac.auca.nkunda.flight_sale_manager.service.IssuedTicketService;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssuedTicketRepository extends JpaRepository<IssuedTicket, UUID> {
    @Query("SELECT d FROM IssuedTicket d "
            + "WHERE d.salesOrder.client.passportNumber = :passportNumber "
            + "ORDER BY d.issuedDate DESC")
    List<IssuedTicket> findTicketsOfClient(@Param("passportNumber") String passportNumber);
    boolean existsBySalesOrder(SalesOrder salesOrder);

}