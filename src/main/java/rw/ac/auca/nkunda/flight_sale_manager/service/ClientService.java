package rw.ac.auca.nkunda.flight_sale_manager.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.nkunda.flight_sale_manager.model.Client;
import rw.ac.auca.nkunda.flight_sale_manager.repository.AgentRepository;
import rw.ac.auca.nkunda.flight_sale_manager.repository.ClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    private final ClientRepository clientRepository;;
    private final AgentRepository agentRepository;

    public ClientService(ClientRepository clientRepository, AgentRepository agentRepository) {
        this.clientRepository = clientRepository;
        this.agentRepository = agentRepository;
    }

    public String saveClient(Client client) {
        clientRepository.save(client);
        return "Client saved successfully";
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(UUID id) {
        return clientRepository.findById(id);
    }

    public String updateClient(UUID id, Client updated) {
        Optional<Client> found = clientRepository.findById(id);
        if (found.isEmpty()) {
            return "The client with that id does not exist";
        }
        Client client = found.get();
        client.setFirstName(updated.getFirstName());
        client.setLastName(updated.getLastName());
        client.setPassportNumber(updated.getPassportNumber());
        client.setEmail(updated.getEmail());
        client.setPhone(updated.getPhone());
        clientRepository.save(client);
        return "Client updated successfully";
    }

    public String deleteClient(UUID id) {
        if (!clientRepository.existsById(id)) {
            return "The client with that id does not exist";
        }
        clientRepository.deleteById(id);
        return "Client deleted successfully";
    }

    // Find by last name
    public List<Client> getClientsByLastName(String lastName) {
        return clientRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    // Find client of an agent
    public Optional<List<Client>> getClientsOfAgent(UUID agentId) {
        if (!agentRepository.existsById(agentId)) {
            return Optional.empty();
        }
        return Optional.of(clientRepository.findClientsOfAgent(agentId));
    }
}