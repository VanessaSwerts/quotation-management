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
import br.inatel.icc.quotationmanagement.model.StockOperation;
import br.inatel.icc.quotationmanagement.repository.StockOperationRepository;
import br.inatel.icc.quotationmanagement.service.StockService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/quote")
@AllArgsConstructor(onConstructor = @__({ @Autowired }))
public class QuoteController {

//	private QuoteRepository quoteRepository;
	private StockOperationRepository stockOperationRepository;
	private StockService stockService;

	@PostMapping
	@Transactional
	public ResponseEntity<?> create(@RequestBody @Valid StockQuoteForm form, UriComponentsBuilder uriBuilder) {

		StockDto stock = stockService.findById(form.getId());

		if (stock == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new FormErrorDto("id", "Não foi possível encontrar um Stock com o id: " + form.getId()));
		}

		StockOperation newOperation = form.convertToStockOperation();

//		for (Quote quote : quotes) {
//			Optional<Quote> alreadyExists = quoteRepository.findByStockIdAndDate(quote.getStock().getStockId(), quote.getDate());
//			
//			if(alreadyExists.isPresent()) {
//				return ResponseEntity.badRequest().build();
//			}
//		}
		stockOperationRepository.save(newOperation);

		URI uri = uriBuilder.path("/quotes/{id}").buildAndExpand(form.getId()).toUri();
		return ResponseEntity.created(uri).body(new StockQuoteDto(newOperation));
	}

	@GetMapping("/{id}")
	@Transactional
	public ResponseEntity<StockQuoteDto> listByStockId(@PathVariable("id") String stockId) {
		StockDto stock = stockService.findById(stockId);

		if (stock == null)
			return ResponseEntity.notFound().build();

		Optional<StockOperation> operation = stockOperationRepository.findByStockId(stockId);
		if (operation.isEmpty())
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(new StockQuoteDto(operation.get()));
	}

	@GetMapping()
	@Transactional
	public ResponseEntity<List<StockQuoteDto>> listAll() {
		List<StockOperation> listStockOperation = stockOperationRepository.findAll();

		return ResponseEntity.ok(StockQuoteDto.convertToStockQuoteDtoList(listStockOperation));
	}

}
