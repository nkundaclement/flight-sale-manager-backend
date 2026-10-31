package rw.ac.auca.nkunda.flight_sale_manager.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.nkunda.flight_sale_manager.model.Agent;
import rw.ac.auca.nkunda.flight_sale_manager.repository.AgentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AgentService {

    private final AgentRepository agentRepository;

    public AgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
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
}