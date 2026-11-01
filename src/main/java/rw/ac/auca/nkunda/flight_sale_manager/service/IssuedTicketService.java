package rw.ac.auca.nkunda.flight_sale_manager.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.nkunda.flight_sale_manager.model.IssuedTicket;
import rw.ac.auca.nkunda.flight_sale_manager.model.OrderStatus;
import rw.ac.auca.nkunda.flight_sale_manager.model.SalesOrder;
import rw.ac.auca.nkunda.flight_sale_manager.repository.IssuedTicketRepository;
import rw.ac.auca.nkunda.flight_sale_manager.repository.SalesOrderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IssuedTicketService {

    private final IssuedTicketRepository issuedTicketRepository;
    private final SalesOrderRepository salesOrderRepository;

    public IssuedTicketService(IssuedTicketRepository issuedTicketRepository,
                               SalesOrderRepository salesOrderRepository) {
        this.issuedTicketRepository = issuedTicketRepository;
        this.salesOrderRepository = salesOrderRepository;
    }

    public String saveIssuedTicket(UUID salesOrderId, IssuedTicket issuedTicket) {
        Optional<SalesOrder> found = salesOrderRepository.findById(salesOrderId);
        if (found.isEmpty()) {
            return "The sales order with that id does not exist";
        }
        SalesOrder salesOrder = found.get();
        if (salesOrder.getStatus() != OrderStatus.PAID) {
            return "Issued tickets are only for PAID sales orders";
        }
        issuedTicket.setIssuedDate(LocalDate.now());
        issuedTicket.setSalesOrder(salesOrder);
        issuedTicketRepository.save(issuedTicket);
        return "Issued ticket saved successfully";
    }

    public List<IssuedTicket> getTicketsOfClient(String passportNumber) {
        return issuedTicketRepository.findTicketsOfClient(passportNumber);
    }

    public String deleteIssuedTicket(UUID id) {
        if (!issuedTicketRepository.existsById(id)) {
            return "The issued ticket with that id does not exist";
        }
        issuedTicketRepository.deleteById(id);
        return "Issued ticket deleted successfully";
    }
}