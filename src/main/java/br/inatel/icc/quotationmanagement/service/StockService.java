package br.inatel.icc.quotationmanagement.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.inatel.icc.quotationmanagement.dto.StockDto;

@Service
public class StockService {

	private static final String BASIC_STOCK_URL = "http://localhost:8080/stock";
	private RestTemplate restTemplate;

	@Autowired
	public StockService() {
		this.restTemplate = new RestTemplate();
	}

	public List<StockDto> findAll() {
		StockDto[] stocks = restTemplate.getForObject(BASIC_STOCK_URL, StockDto[].class);

		return Arrays.asList(stocks);
	}

	public StockDto findById(String id) {
		return restTemplate.getForObject(BASIC_STOCK_URL +  "/" + id, StockDto.class);
	}

}
