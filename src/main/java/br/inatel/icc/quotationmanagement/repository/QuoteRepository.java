package br.inatel.icc.quotationmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.icc.quotationmanagement.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, String> {

	List<Quote> findByStockId(String stockId);

}
