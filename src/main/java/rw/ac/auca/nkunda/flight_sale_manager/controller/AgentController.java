package rw.ac.auca.nkunda.flight_sale_manager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.nkunda.flight_sale_manager.model.Agent;
import rw.ac.auca.nkunda.flight_sale_manager.service.AgentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveAgent(@RequestBody Agent agent) {
        String message = agentService.saveAgent(agent);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Agent>> getAllAgents() {
        return ResponseEntity.ok(agentService.getAllAgents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAgentById(@PathVariable UUID id) {
        Optional<Agent> found = agentService.getAgentById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok(found.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The agent with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAgent(@PathVariable UUID id, @RequestBody Agent agent) {
        String message = agentService.updateAgent(id, agent);
        if (message.equals("Agent updated successfully")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAgent(@PathVariable UUID id) {
        String message = agentService.deleteAgent(id);
        if (message.equals("Agent deleted successfully")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    // search agent by airline
    @GetMapping("/by-airline")
    public ResponseEntity<List<Agent>> getAgentsByAirline(@RequestParam String name) {
        return ResponseEntity.ok(agentService.getAgentsByAirline(name));
    }

    // add agent to airline
    @PutMapping("/{agentId}/airlines/{airlineId}")
    public ResponseEntity<String> addAirlineToAgent(@PathVariable UUID agentId,
                                                    @PathVariable UUID airlineId) {
        String message = agentService.addAirlineToAgent(agentId, airlineId);
        if (message.equals("Airline added to the agent")) {
            return ResponseEntity.ok(message);
        } else if (message.equals("The agent already has that airline")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}