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
import rw.ac.auca.nkunda.flight_sale_manager.model.Airline;
import rw.ac.auca.nkunda.flight_sale_manager.service.AirlineService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveAirline(@RequestBody Airline airline) {
        String message = airlineService.saveAirline(airline);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Airline>> getAllAirlines() {
        return ResponseEntity.ok(airlineService.getAllAirlines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAirlineById(@PathVariable UUID id) {
        Optional<Airline> found = airlineService.getAirlineById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The airline with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAirline(@PathVariable UUID id, @RequestBody Airline airline) {
        String message = airlineService.updateAirline(id, airline);
        if (message.equals("Airline updated successfully")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAirline(@PathVariable UUID id) {
        String message = airlineService.deleteAirline(id);
        if (message.equals("Airline deleted successfully")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @GetMapping("/unused")
    public ResponseEntity<List<Airline>> getUnusedAirlines() {
        return ResponseEntity.ok(airlineService.getUnusedAirlines());
    }
}