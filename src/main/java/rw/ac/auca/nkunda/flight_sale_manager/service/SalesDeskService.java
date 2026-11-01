package rw.ac.auca.nkunda.flight_sale_manager.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.nkunda.flight_sale_manager.model.SalesDesk;
import rw.ac.auca.nkunda.flight_sale_manager.repository.SalesDeskRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SalesDeskService {

    private final SalesDeskRepository salesDeskRepository;

    public SalesDeskService(SalesDeskRepository salesDeskRepository) {
        this.salesDeskRepository = salesDeskRepository;
    }

    public String saveSalesDesk(SalesDesk salesDesk) {
        salesDeskRepository.save(salesDesk);
        return "Sales desk saved successfully";
    }

    public List<SalesDesk> getAllSalesDesks() {
        return salesDeskRepository.findAll();
    }

    public Optional<SalesDesk> getSalesDeskById(UUID id) {
        return salesDeskRepository.findById(id);
    }

    public String updateSalesDesk(UUID id, SalesDesk updated) {
        Optional<SalesDesk> found = salesDeskRepository.findById(id);
        if (found.isEmpty()) {
            return "The sales desk with that id does not exist";
        }
        SalesDesk salesDesk = found.get();
        salesDesk.setDeskNumber(updated.getDeskNumber());
        salesDesk.setName(updated.getName());
        salesDeskRepository.save(salesDesk);
        return "Sales desk updated successfully";
    }

    public String deleteSalesDesk(UUID id) {
        if (!salesDeskRepository.existsById(id)) {
            return "The sales desk with that id does not exist";
        }
        salesDeskRepository.deleteById(id);
        return "Sales desk deleted successfully";
    }

    public List<SalesDesk> getFreeSalesDesks() {
        return salesDeskRepository.findFreeSalesDesks();
    }
}