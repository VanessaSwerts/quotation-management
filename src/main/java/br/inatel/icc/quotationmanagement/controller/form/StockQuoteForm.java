package br.inatel.icc.quotationmanagement.controller.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import br.inatel.icc.quotationmanagement.model.Quote;

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

	public List<Quote> convertQuotesMapToList() {
		List<Quote> quotesList = new ArrayList<>();

		for (Map.Entry<String, String> quote : this.quotes.entrySet()) {

			LocalDate date = LocalDate.parse(quote.getKey());
			BigDecimal price = new BigDecimal(quote.getValue());

			quotesList.add(new Quote(this.id, date, price));
		}

		return quotesList;
	}

}
