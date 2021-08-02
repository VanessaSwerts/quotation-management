package br.inatel.icc.quotationmanagement.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.icc.quotationmanagement.controller.form.StockQuoteForm;
import br.inatel.icc.quotationmanagement.dto.StockQuoteDto;
import br.inatel.icc.quotationmanagement.model.Quote;
import br.inatel.icc.quotationmanagement.repository.QuoteRepository;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/quote")
@AllArgsConstructor(onConstructor = @__({ @Autowired }))
public class QuoteController {

	QuoteRepository quoteRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<StockQuoteDto> create(@RequestBody @Valid StockQuoteForm form,
			UriComponentsBuilder uriBuilder) {

		List<Quote> quotes = form.convertQuotesMapToList();
		quoteRepository.saveAll(quotes);

		URI uri = uriBuilder.path("/quotes/{id}").buildAndExpand(form.getId()).toUri();
		return ResponseEntity.created(uri).body(new StockQuoteDto(form.getId(), quotes));
	}

	@GetMapping("/{id}")
	@Transactional
	public ResponseEntity<StockQuoteDto> listByStockId(@PathVariable("id") String stockId) {
		
		List<Quote> quotes = quoteRepository.findByStockId(stockId);

		return ResponseEntity.ok(new StockQuoteDto(stockId, quotes));
	}
	
}
