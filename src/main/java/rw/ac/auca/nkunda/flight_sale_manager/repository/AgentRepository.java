package rw.ac.auca.nkunda.flight_sale_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.ac.auca.nkunda.flight_sale_manager.model.Agent;

import java.util.List;
import java.util.UUID;

@Repository
public interface AgentRepository extends JpaRepository<Agent, UUID> {
    @Query("SELECT a FROM Agent a JOIN a.airlines al WHERE LOWER(al.name) = LOWER(:name)")
    List<Agent> findAgentsHoldingAirline(@Param("name") String name);
}