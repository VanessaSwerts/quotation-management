package br.inatel.icc.quotationmanagement.service;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.inatel.icc.quotationmanagement.dto.StockDto;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StockService {

	private RestTemplate restTemplate;

	private String basicStockURL;
	private String basicNotificationURL;

	@Autowired
	public StockService(@Value("${stock-manager.url}") String stockManagerURL) {
		this.restTemplate = new RestTemplate();

		this.basicStockURL = stockManagerURL + "/stock";
		this.basicNotificationURL = stockManagerURL + "/notification";
		System.out.println("URL ---> " + basicStockURL);
	}

	@Cacheable(value = "allStocks")
	public List<StockDto> findAll() {
		log.info("Getting all stocks from external API!");
		StockDto[] stocks = restTemplate.getForObject(basicStockURL, StockDto[].class);

		return Arrays.asList(stocks);
	}

	@Cacheable(value = "stockById")
	public StockDto findById(String id) {
		log.info("Getting the stock with id " + id + " from external API!");
		return restTemplate.getForObject(basicStockURL + "/" + id, StockDto.class);
	}

	public void registerNotification() {
		log.info("Registering for notification!");

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);

		JSONObject body = new JSONObject();
		body.put("host", "localhost");
		body.put("port", 8081);

		HttpEntity<String> request = new HttpEntity<String>(body.toString(), header);

		restTemplate.postForObject(basicNotificationURL, request, String.class);
	}

}
