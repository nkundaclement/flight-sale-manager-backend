package rw.ac.auca.nkunda.flight_sale_manager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.ac.auca.nkunda.flight_sale_manager.model.SalesDesk;
import rw.ac.auca.nkunda.flight_sale_manager.service.SalesDeskService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/sales-desks")
public class SalesDeskController {

    private final SalesDeskService salesDeskService;

    public SalesDeskController(SalesDeskService salesDeskService) {
        this.salesDeskService = salesDeskService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveSalesDesk(@RequestBody SalesDesk salesDesk) {
        String message = salesDeskService.saveSalesDesk(salesDesk);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SalesDesk>> getAllSalesDesks() {
        return ResponseEntity.ok(salesDeskService.getAllSalesDesks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSalesDeskById(@PathVariable UUID id) {
        Optional<SalesDesk> found = salesDeskService.getSalesDeskById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The sales desk with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSalesDesk(@PathVariable UUID id, @RequestBody SalesDesk salesDesk) {
        String message = salesDeskService.updateSalesDesk(id, salesDesk);
        if (message.equals("Sales desk updated successfully")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSalesDesk(@PathVariable UUID id) {
        String message = salesDeskService.deleteSalesDesk(id);
        if (message.equals("Sales desk deleted successfully")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}