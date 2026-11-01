package rw.ac.auca.nkunda.flight_sale_manager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.nkunda.flight_sale_manager.model.OrderStatus;
import rw.ac.auca.nkunda.flight_sale_manager.model.SalesOrder;
import rw.ac.auca.nkunda.flight_sale_manager.service.SalesOrderService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/sales-orders")
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    public SalesOrderController(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveSalesOrder(@RequestBody SalesOrder salesOrder) {
        String message = salesOrderService.saveSalesOrder(salesOrder);
        if (message.equals("Sales order saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } else if (message.equals("Sales order date cannot be in the past")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }

    @GetMapping("/all                       ")
    public ResponseEntity<List<SalesOrder>> getAllSalesOrders() {
        return ResponseEntity.ok(salesOrderService.getAllSalesOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSalesOrderById(@PathVariable UUID id) {
        Optional<SalesOrder> found = salesOrderService.getSalesOrderById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The sales order with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSalesOrder(@PathVariable UUID id, @RequestBody SalesOrder salesOrder) {
        String message = salesOrderService.updateSalesOrder(id, salesOrder);
        if (message.equals("Sales order updated successfully")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSalesOrder(@PathVariable UUID id) {
        String message = salesOrderService.deleteSalesOrder(id);
        if (message.equals("Sales order deleted successfully")) {
            return ResponseEntity.ok(message);
        } else if (message.equals("That sales order has issued tickets, delete them first")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    // find by order status
    @GetMapping("/by-status")
    public ResponseEntity<List<SalesOrder>> getSalesOrdersByStatus(@RequestParam OrderStatus status) {
        return ResponseEntity.ok(salesOrderService.getSalesOrdersByStatus(status));
    }

    // Find order between two given date
    @GetMapping("/between")
    public ResponseEntity<List<SalesOrder>> getSalesOrdersBetween(@RequestParam String start,
                                                                  @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return ResponseEntity.ok(salesOrderService.getSalesOrdersBetween(startDate, endDate));
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> getSalesOrderCountsByStatus() {
        return ResponseEntity.ok(salesOrderService.getSalesOrderCountsByStatus());
    }

}