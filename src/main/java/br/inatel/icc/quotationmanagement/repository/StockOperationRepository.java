package br.inatel.icc.quotationmanagement.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.icc.quotationmanagement.model.StockOperation;

public interface StockOperationRepository extends JpaRepository<StockOperation, String> {

	List<StockOperation> findByStockId(String stockId);

	Optional<StockOperation> findByStockIdAndQuotesDate(String stockId, LocalDate date);

}
