package rw.ac.auca.nkunda.flight_sale_manager.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.nkunda.flight_sale_manager.model.Agent;
import rw.ac.auca.nkunda.flight_sale_manager.model.Airline;
import rw.ac.auca.nkunda.flight_sale_manager.model.SalesDesk;
import rw.ac.auca.nkunda.flight_sale_manager.repository.AgentRepository;
import rw.ac.auca.nkunda.flight_sale_manager.repository.AirlineRepository;
import rw.ac.auca.nkunda.flight_sale_manager.repository.SalesDeskRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AgentService {

    private final AgentRepository agentRepository;
    private final AirlineRepository airlineRepository;
    private final SalesDeskRepository salesDeskRepository;

    public AgentService(AgentRepository agentRepository, AirlineRepository airlineRepository, SalesDeskRepository salesDeskRepository) {
        this.agentRepository = agentRepository;
        this.airlineRepository = airlineRepository;
        this.salesDeskRepository = salesDeskRepository;
    }

    public String saveAgent(Agent agent) {
        agentRepository.save(agent);
        return "Agent saved successfully";
    }

    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    public Optional<Agent> getAgentById(UUID id) {
        return agentRepository.findById(id);
    }

    public String updateAgent(UUID id, Agent updated) {
        Optional<Agent> found = agentRepository.findById(id);
        if (found.isEmpty()) {
            return "The agent with that id does not exist";
        }
        Agent agent = found.get();
        agent.setFirstName(updated.getFirstName());
        agent.setLastName(updated.getLastName());
        agent.setEmail(updated.getEmail());
        agent.setSalesDesk(updated.getSalesDesk());
        agent.setAirlines(updated.getAirlines());
        agentRepository.save(agent);
        return "Agent updated successfully";
    }

    public String deleteAgent(UUID id) {
        if (!agentRepository.existsById(id)) {
            return "The agent with that id does not exist";
        }
        agentRepository.deleteById(id);
        return "Agent deleted successfully";
    }

    // search agent by airline
    public List<Agent> getAgentsByAirline(String name) {
        return agentRepository.findAgentsHoldingAirline(name);
    }

    // add airline to agent
    public String addAirlineToAgent(UUID agentId, UUID airlineId) {
        Optional<Agent> foundAgent = agentRepository.findById(agentId);
        if (foundAgent.isEmpty()) {
            return "The agent with that id does not exist";
        }
        Optional<Airline> foundAirline = airlineRepository.findById(airlineId);
        if (foundAirline.isEmpty()) {
            return "That airline does not exist";
        }
        Agent agent = foundAgent.get();
        boolean alreadyHeld = agent.getAirlines().stream()
                .anyMatch(a -> a.getId().equals(airlineId));
        if (alreadyHeld) {
            return "The agent already has that airline";
        }
        agent.getAirlines().add(foundAirline.get());
        agentRepository.save(agent);
        return "Airline added to the agent";
    }

    public String changeSalesDesk(UUID agentId, int deskNumber) {
        Optional<Agent> foundAgent = agentRepository.findById(agentId);
        if (foundAgent.isEmpty()) {
            return "The agent with that id does not exist";
        }
        Optional<SalesDesk> foundDesk = salesDeskRepository.findByDeskNumber(deskNumber);
        if (foundDesk.isEmpty()) {
            return "The sales desk with that deskNumber does not exist";
        }
        if (agentRepository.existsBySalesDesk(foundDesk.get())) {
            return "That sales desk is taken by another agent";
        }
        Agent agent = foundAgent.get();
        agent.setSalesDesk(foundDesk.get());
        agentRepository.save(agent);
        return "Sales desk changed successfully";
    }
}