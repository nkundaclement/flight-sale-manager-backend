package rw.ac.auca.nkunda.flight_sale_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.ac.auca.nkunda.flight_sale_manager.model.Agent;
import rw.ac.auca.nkunda.flight_sale_manager.model.OrderStatus;
import rw.ac.auca.nkunda.flight_sale_manager.model.SalesOrder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, UUID> {
    List<SalesOrder> findByStatusOrderByOrderDateAsc(OrderStatus status);
    List<SalesOrder> findByOrderDateBetweenOrderByOrderDateAsc(LocalDate start, LocalDate end);
    boolean existsByAgentAndOrderDateAndStatusNot(Agent agent, LocalDate orderDate, OrderStatus status);
    @Query("SELECT s.status, COUNT(s) FROM SalesOrder s GROUP BY s.status")
    List<Object[]> countSalesOrdersByStatus();
    @Modifying
    @Query("UPDATE SalesOrder s SET s.status = :cancelled "
            + "WHERE s.agent.id = :agentId AND s.orderDate = :date AND s.status <> :paid")
    int cancelOrdersOfAgentOnDate(@Param("agentId") UUID agentId, @Param("date") LocalDate date,
                                  @Param("cancelled") OrderStatus cancelled,
                                  @Param("paid") OrderStatus paid);
}