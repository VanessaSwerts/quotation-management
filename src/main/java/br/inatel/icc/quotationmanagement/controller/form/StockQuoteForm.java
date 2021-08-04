package br.inatel.icc.quotationmanagement.controller.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import br.inatel.icc.quotationmanagement.model.Quote;
import br.inatel.icc.quotationmanagement.model.StockOperation;

public class StockQuoteForm {

	@NotNull
	@NotEmpty
	private String id;

	@NotNull
	@NotEmpty
	private Map<String, String> quotes;

	public String getId() {
		return id;
	}

	public Map<String, String> getQuotes() {
		return quotes;
	}

	public List<Quote> convertQuotesMapToList(StockOperation stockOperation) {
		List<Quote> quotesList = new ArrayList<>();

		for (Map.Entry<String, String> quote : this.quotes.entrySet()) {

			LocalDate date = LocalDate.parse(quote.getKey());
			BigDecimal price = new BigDecimal(quote.getValue());

			quotesList.add(new Quote(date, price, stockOperation));
		}

		return quotesList;
	}

	public StockOperation convertToStockOperation() {
		StockOperation newStockOperation = new StockOperation(id);
		List<Quote> newQuotes = convertQuotesMapToList(newStockOperation);
		newStockOperation.setQuotes(newQuotes);

		return newStockOperation;
	}

	public boolean isQuotesValid() {

		for (Map.Entry<String, String> quote : this.quotes.entrySet()) {
			String date = quote.getKey();
			String price = quote.getValue();

			if (!date.matches("^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")) {
				return false;
			}

			if (!price.matches("^[0-9]*([\\\\.,]{1}[0-9]{0,2}){0,1}$")) {
				return false;
			}
		}

		return true;
	}

}
