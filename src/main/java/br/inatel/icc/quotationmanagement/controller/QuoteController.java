package br.inatel.icc.quotationmanagement.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.icc.quotationmanagement.configs.validation.FormErrorDto;
import br.inatel.icc.quotationmanagement.controller.form.StockQuoteForm;
import br.inatel.icc.quotationmanagement.dto.StockDto;
import br.inatel.icc.quotationmanagement.dto.StockQuoteDto;
import br.inatel.icc.quotationmanagement.model.Quote;
import br.inatel.icc.quotationmanagement.model.StockOperation;
import br.inatel.icc.quotationmanagement.repository.StockOperationRepository;
import br.inatel.icc.quotationmanagement.service.StockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/quote")
@AllArgsConstructor(onConstructor = @__({ @Autowired }))
@Slf4j
public class QuoteController {

	private StockOperationRepository stockOperationRepository;
	private StockService stockService;

	@PostMapping
	@Transactional
	public ResponseEntity<?> create(@RequestBody @Valid StockQuoteForm form, UriComponentsBuilder uriBuilder) {

		StockDto stock = stockService.findById(form.getId());

		if (stock == null) {
			log.error("Stock with id " + form.getId() + "not found!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new FormErrorDto("id", "Could not find a stock with id: " + form.getId()));
		}

		if(!form.isQuotesValid()) {
			log.error("Date or Price is not valid!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new FormErrorDto("quotes", "Date or Price is not valid!"));
		}
		
		StockOperation newOperation = form.convertToStockOperation();
		List<Quote> quotes = newOperation.getQuotes();

		for (Quote quote : quotes) {
			Optional<StockOperation> alreadyExists = stockOperationRepository
					.findByStockIdAndQuotesDate(newOperation.getStockId(), quote.getDate());

			if (alreadyExists.isPresent()) {
				log.error("Already exist a price to date " + quote.getDate() + " in stock with id : " + form.getId());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FormErrorDto("quotes",
						"Already exist a price to date " + quote.getDate() + " in stock with id : " + form.getId()));
			}
		}

		stockOperationRepository.save(newOperation);

		log.info("Saving quotes to the stock with id: " + form.getId());
		URI uri = uriBuilder.path("/quotes/{id}").buildAndExpand(form.getId()).toUri();
		return ResponseEntity.created(uri).body(new StockQuoteDto(newOperation));
	}

	@GetMapping("/{id}")
	@Transactional
	public ResponseEntity<?> listByStockId(@PathVariable("id") String stockId) {
		StockDto stock = stockService.findById(stockId);

		if (stock == null) {
			log.error("Stock with id " + stockId + "not found!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new FormErrorDto("id", "Could not find a stock with id: " + stockId));
		}

		List<StockOperation> listStockOperation = stockOperationRepository.findByStockId(stockId);
		if (listStockOperation.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new FormErrorDto("id", "No quotes operations to the stock with id : " + stockId));

		log.info("Listing all quotes operations to the stock with id: " + stockId);
		return ResponseEntity.ok(StockQuoteDto.convertToStockQuoteDtoList(listStockOperation));
	}

	@GetMapping()
	@Transactional
	public ResponseEntity<?> listAll() {
		List<StockOperation> listStockOperation = stockOperationRepository.findAll();

		if (listStockOperation.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new FormErrorDto("id", "Stock quotes operations not found!"));

		log.info("Listing all stock quotes operations");
		return ResponseEntity.ok(StockQuoteDto.convertToStockQuoteDtoList(listStockOperation));
	}

}
