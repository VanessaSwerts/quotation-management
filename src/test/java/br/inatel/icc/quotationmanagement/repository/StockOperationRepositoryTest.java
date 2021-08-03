package br.inatel.icc.quotationmanagement.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.inatel.icc.quotationmanagement.model.Quote;
import br.inatel.icc.quotationmanagement.model.StockOperation;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
class StockOperationRepositoryTest {

	@Autowired
	StockOperationRepository stockOperationrepository;

	@BeforeEach
	public void beforeEach() {
		StockOperation stockOperation = new StockOperation("petr4");

		List<Quote> quotes = new ArrayList<>();
		quotes.add(new Quote(LocalDate.parse("2021-08-01"), new BigDecimal("10"), stockOperation));
		quotes.add(new Quote(LocalDate.parse("2021-08-02"), new BigDecimal("11"), stockOperation));
		quotes.add(new Quote(LocalDate.parse("2021-08-03"), new BigDecimal("14"), stockOperation));
		
		stockOperation.setQuotes(quotes);
		stockOperationrepository.save(stockOperation);
	}

	@Test
	void shoudlFindByStockId() {
		String id = "petr4";

		List<StockOperation> stockOperation = stockOperationrepository.findByStockId(id);

		Assert.assertEquals(stockOperation.size(), 1);
		Assert.assertEquals(stockOperation.get(0).getQuotes().size(), 3);
		Assert.assertEquals(stockOperation.get(0).getStockId(), id);
	}
	
	@Test
	void shoudlNotFindByStockId() {
		String id = "vale5";

		List<StockOperation> stockOperation = stockOperationrepository.findByStockId(id);

		Assert.assertTrue(stockOperation.isEmpty());
	}
	
	@Test
	void shouldFindByStockIdAndQuotesDate() {
		String id = "petr4";

		Optional<StockOperation> stockOperation = stockOperationrepository.findByStockIdAndQuotesDate(id, LocalDate.parse("2021-08-02"));

		Assert.assertTrue(stockOperation.isPresent());
	}
	
	

}
