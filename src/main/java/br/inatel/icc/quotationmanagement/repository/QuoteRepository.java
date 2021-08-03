package br.inatel.icc.quotationmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.icc.quotationmanagement.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, String> {

}
