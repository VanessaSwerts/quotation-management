package br.inatel.icc.quotationmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.icc.quotationmanagement.model.StockOperation;

public interface StockOperationRepository extends JpaRepository<StockOperation, String> {

	Optional<StockOperation> findByStockId(String stockId);

}
