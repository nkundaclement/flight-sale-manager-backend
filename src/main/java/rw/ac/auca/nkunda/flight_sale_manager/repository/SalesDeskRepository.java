package rw.ac.auca.nkunda.flight_sale_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.auca.nkunda.flight_sale_manager.model.SalesDesk;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SalesDeskRepository extends JpaRepository<SalesDesk, UUID> {
    @Query("SELECT d FROM SalesDesk d WHERE NOT EXISTS "
            + "(SELECT a FROM Agent a WHERE a.salesDesk = d) ORDER BY d.deskNumber ASC")
    List<SalesDesk> findFreeSalesDesks();
    Optional<SalesDesk> findByDeskNumber(int deskNumber);
}