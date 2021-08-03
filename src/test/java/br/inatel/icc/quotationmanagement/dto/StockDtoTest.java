package br.inatel.icc.quotationmanagement.dto;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class StockDtoTest {
	
	private StockDto newStockDto;
	
	@Test
	void verifyStockDtoAFterCreate() {
		this.newStockDto = new StockDto("petr7", "Test new stock!");
		
		Assert.assertEquals("petr7", newStockDto.getId());
		Assert.assertEquals("Test new stock!", newStockDto.getDescription());
	}

}
