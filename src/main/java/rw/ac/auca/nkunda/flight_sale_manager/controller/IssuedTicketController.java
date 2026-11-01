package rw.ac.auca.nkunda.flight_sale_manager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.nkunda.flight_sale_manager.model.IssuedTicket;
import rw.ac.auca.nkunda.flight_sale_manager.service.IssuedTicketService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/issued-tickets")
public class IssuedTicketController {

    private final IssuedTicketService issuedTicketService;

    public IssuedTicketController(IssuedTicketService issuedTicketService) {
        this.issuedTicketService = issuedTicketService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveIssuedTicket(@RequestParam UUID salesOrderId,
                                                   @RequestBody IssuedTicket issuedTicket) {
        String message = issuedTicketService.saveIssuedTicket(salesOrderId, issuedTicket);
        if (message.equals("Issued ticket saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } else if (message.equals("Issued tickets are only for PAID sales orders")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @GetMapping("/client/{passportNumber}")
    public ResponseEntity<List<IssuedTicket>> getTicketsOfClient(@PathVariable String passportNumber) {
        return ResponseEntity.ok(issuedTicketService.getTicketsOfClient(passportNumber));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteIssuedTicket(@PathVariable UUID id) {
        String message = issuedTicketService.deleteIssuedTicket(id);
        if (message.equals("Issued ticket deleted successfully")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}