package rw.ac.auca.nkunda.flight_sale_manager.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.nkunda.flight_sale_manager.model.OrderStatus;
import rw.ac.auca.nkunda.flight_sale_manager.model.SalesOrder;
import rw.ac.auca.nkunda.flight_sale_manager.repository.IssuedTicketRepository;
import rw.ac.auca.nkunda.flight_sale_manager.repository.SalesOrderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final IssuedTicketRepository issuedTicketRepository;

    public SalesOrderService(SalesOrderRepository salesOrderRepository, IssuedTicketRepository issuedTicketRepository) {
        this.salesOrderRepository = salesOrderRepository;
        this.issuedTicketRepository = issuedTicketRepository;
    }

    public String saveSalesOrder(SalesOrder salesOrder) {
        if (salesOrder.getOrderDate().isBefore(LocalDate.now())) {
            return "Sales order date cannot be in the past";
        }
        if (salesOrderRepository.existsByAgentAndOrderDateAndStatusNot(
                salesOrder.getAgent(), salesOrder.getOrderDate(), OrderStatus.CANCELLED)) {
            return "Agent already has an order on that date";
        }
        salesOrder.setStatus(OrderStatus.QUOTED);
        salesOrderRepository.save(salesOrder);
        return "Sales order saved successfully";
    }

    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAll();
    }

    public Optional<SalesOrder> getSalesOrderById(UUID id) {
        return salesOrderRepository.findById(id);
    }

    public String updateSalesOrder(UUID id, SalesOrder updated) {
        Optional<SalesOrder> found = salesOrderRepository.findById(id);
        if (found.isEmpty()) {
            return "The sales order with that id does not exist";
        }
        SalesOrder salesOrder = found.get();
        salesOrder.setOrderDate(updated.getOrderDate());
        salesOrder.setStatus(updated.getStatus());
        salesOrder.setTotalAmount(updated.getTotalAmount());
        salesOrder.setClient(updated.getClient());
        salesOrder.setAgent(updated.getAgent());
        salesOrderRepository.save(salesOrder);
        return "Sales order updated successfully";
    }

    // find by order status
    public List<SalesOrder> getSalesOrdersByStatus(OrderStatus status) {
        return salesOrderRepository.findByStatusOrderByOrderDateAsc(status);
    }

    // find order between two date
    public List<SalesOrder> getSalesOrdersBetween(LocalDate start, LocalDate end) {
        return salesOrderRepository.findByOrderDateBetweenOrderByOrderDateAsc(start, end);
    }

    public String deleteSalesOrder(UUID id) {
        Optional<SalesOrder> found = salesOrderRepository.findById(id);
        if (found.isEmpty()) {
            return "The sales order with that id does not exist";
        }
        if (issuedTicketRepository.existsBySalesOrder(found.get())) {
            return "That sales order has issued tickets, delete them first";
        }
        salesOrderRepository.deleteById(id);
        return "Sales order deleted successfully";
    }

    public List<Object[]> getSalesOrderCountsByStatus() {
        return salesOrderRepository.countSalesOrdersByStatus();
    }
}