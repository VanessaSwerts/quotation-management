package br.inatel.icc.quotationmanagement.acceptance.steps;

import static org.hamcrest.CoreMatchers.containsString;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.inatel.icc.quotationmanagement.acceptance.CucumberSpringConfiguration;
import br.inatel.icc.quotationmanagement.dto.StockDto;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StockOperationSteps extends CucumberSpringConfiguration {

	private JSONObject body;
	private JSONObject quotes;

	private StockDto stock;
	private List<StockDto> stocks;
	private String getStockId;

	private ResultActions response;

	@Before
	public void before() {
		this.body = new JSONObject();
		this.quotes = new JSONObject();
		this.stocks = new ArrayList<>();
	}

	@Given("a stock with id {string} and the quote with {string} and {string}")
	public void a_stock_with_id_and_the_quote_with_and(String stockId, String date, String price) throws JSONException {

		this.stock = new StockDto(stockId, "");
		stocks.add(stock);

		Mockito.when(stockService.findById(stockId)).thenReturn(stock);

		quotes.put(date, price);
		body.put("id", stockId);
		body.put("quotes", quotes);
	}

	@When("client request the api")
	public void client_request_the_api() throws Exception {
		response = mockMvc.perform(
				MockMvcRequestBuilders.post("/quote").content(body.toString()).contentType(MediaType.APPLICATION_JSON));
	}

	@Then("the quote is created and the client receive the response status and JSON with {string} and quotes")
	public void the_quote_is_created_and_the_client_receive_the_response_status_and_json_with_and_quotes(String stockId)
			throws Exception {
		response.andExpect(MockMvcResultMatchers.status().is(201))
				.andExpect(MockMvcResultMatchers.jsonPath("$.stockId").value(stockId))
				.andExpect(MockMvcResultMatchers.content().string(containsString("quotes")));
	}

	@Given("a stock with id {string}")
	public void a_stock_with_id(String stockId) {
		this.getStockId = stockId;
		Mockito.when(stockService.findById(stockId)).thenReturn(null);
	}

	@When("client request the api passing an stockId that dont exists")
	public void client_request_the_api_passing_an_stock_id_that_exists() throws Exception {
		response = mockMvc
				.perform(MockMvcRequestBuilders.get("/quote/" + getStockId).contentType(MediaType.APPLICATION_JSON));

	}

	@Then("the client receive the response status {int} and an error {string}")
	public void the_client_receive_the_response_status_and_an_error(Integer status, String error) throws Exception {
		response.andExpect(MockMvcResultMatchers.status().is(status))
				.andExpect(MockMvcResultMatchers.jsonPath("$.field").value("id"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(error));
	}

}
