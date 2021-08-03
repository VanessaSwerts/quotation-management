package br.inatel.icc.quotationmanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.inatel.icc.quotationmanagement.model.Quote;
import br.inatel.icc.quotationmanagement.model.StockOperation;

class StockQuoteDtoTest {

	private List<Quote> newQuotes;
	private StockOperation newOperation;
	private StockQuoteDto stockQuoteDto;

	@BeforeEach
	public void beforeEach() {
		this.newQuotes = new ArrayList<>();
		this.newOperation = new StockOperation("petr4");
		
		this.newQuotes.add(new Quote(LocalDate.parse("2021-08-01"), new BigDecimal("10"), newOperation));
		this.newQuotes.add(new Quote(LocalDate.parse("2021-08-02"), new BigDecimal("11"), newOperation));
		
		this.newOperation.setQuotes(newQuotes);
	}
	
	
	@Test
	public void verifyStockQuoteDtoAFterCreate() {
		this.stockQuoteDto = new StockQuoteDto(newOperation);
		
		
		Assert.assertEquals("petr4", stockQuoteDto.getStockId());
		Assert.assertEquals(2, stockQuoteDto.getQuotes().size());
	}

}
