package rw.ac.auca.nkunda.flight_sale_manager.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.nkunda.flight_sale_manager.model.SalesOrder;
import rw.ac.auca.nkunda.flight_sale_manager.repository.SalesOrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;

    public SalesOrderService(SalesOrderRepository salesOrderRepository) {
        this.salesOrderRepository = salesOrderRepository;
    }

    public String saveSalesOrder(SalesOrder salesOrder) {
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

    public String deleteSalesOrder(UUID id) {
        if (!salesOrderRepository.existsById(id)) {
            return "The sales order with that id does not exist";
        }
        salesOrderRepository.deleteById(id);
        return "Sales order deleted successfully";
    }
}