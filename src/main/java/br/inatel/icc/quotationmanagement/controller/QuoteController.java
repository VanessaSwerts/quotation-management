package br.inatel.icc.quotationmanagement.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
import br.inatel.icc.quotationmanagement.dto.StockDto;
import br.inatel.icc.quotationmanagement.dto.StockQuoteDto;
import br.inatel.icc.quotationmanagement.model.Quote;
import br.inatel.icc.quotationmanagement.repository.QuoteRepository;
import br.inatel.icc.quotationmanagement.service.StockService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/quote")
@AllArgsConstructor(onConstructor = @__({ @Autowired }))
public class QuoteController {

	private QuoteRepository quoteRepository;
	private StockService stockService;

	@PostMapping
	@Transactional
	public ResponseEntity<StockQuoteDto> create(@RequestBody @Valid StockQuoteForm form,
			UriComponentsBuilder uriBuilder) {

		StockDto stock = stockService.findById(form.getId());

		if (stock == null) {
			return ResponseEntity.notFound().build();
		}

		List<Quote> quotes = form.convertQuotesMapToList();
		quoteRepository.saveAll(quotes);

		URI uri = uriBuilder.path("/quotes/{id}").buildAndExpand(form.getId()).toUri();
		return ResponseEntity.created(uri).body(new StockQuoteDto(form.getId(), quotes));
	}

	@GetMapping("/{id}")
	@Transactional
	public ResponseEntity<StockQuoteDto> listByStockId(@PathVariable("id") String stockId) {
		StockDto stock = stockService.findById(stockId);

		if (stock == null)
			return ResponseEntity.notFound().build();

		List<Quote> quotes = quoteRepository.findByStockId(stockId);

		return ResponseEntity.ok(new StockQuoteDto(stockId, quotes));
	}

	@GetMapping()
	@Transactional
	public ResponseEntity<List<StockQuoteDto>> listAll() {
		List<StockDto> stocksList = stockService.findAll();

		List<StockQuoteDto> stockQuotesList = stocksList.stream().map(stock -> {
			List<Quote> quotesList = quoteRepository.findByStockId(stock.getId());
			return new StockQuoteDto(stock.getId(), quotesList);

		}).collect(Collectors.toList());

		return ResponseEntity.ok(stockQuotesList);
	}

}
