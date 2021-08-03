package br.inatel.icc.quotationmanagement.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.inatel.icc.quotationmanagement.model.Quote;
import br.inatel.icc.quotationmanagement.model.StockOperation;

public class StockQuoteDto {

	private String id;
	private String stockId;
	private Map<String, String> quotes = new HashMap<String, String>();

	public StockQuoteDto(StockOperation stockOperation) {
		this.id = stockOperation.getUUID().toString();
		this.stockId = stockOperation.getStockId();
		this.quotes = convertQuotesListToMap(stockOperation.getQuotes());
	}

	public String getId() {
		return id;
	}

	public String getStockId() {
		return stockId;
	}

	public Map<String, String> getQuotes() {
		return quotes;
	}

	private Map<String, String> convertQuotesListToMap(List<Quote> quotesList) {
		Map<String, String> quotesMap = new HashMap<>();

		quotesList.forEach(quote -> {
			String date = quote.getDate().toString();
			String price = quote.getPrice().toBigInteger().toString();

			quotesMap.put(date, price);
		});

		return quotesMap;
	}

	public static List<StockQuoteDto> convertToStockQuoteDtoList(List<StockOperation> listStockOperation) {
		return listStockOperation.stream().map(StockQuoteDto::new).collect(Collectors.toList());
	}

}
