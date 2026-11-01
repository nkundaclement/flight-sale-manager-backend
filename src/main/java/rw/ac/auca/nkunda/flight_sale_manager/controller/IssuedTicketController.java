package rw.ac.auca.nkunda.flight_sale_manager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rw.ac.auca.nkunda.flight_sale_manager.model.IssuedTicket;
import rw.ac.auca.nkunda.flight_sale_manager.service.IssuedTicketService;

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
}