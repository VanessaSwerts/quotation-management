package br.inatel.icc.quotationmanagement.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.inatel.icc.quotationmanagement.dto.StockDto;
import br.inatel.icc.quotationmanagement.service.StockService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class QuoteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StockService stockService;
	private StockDto stock1;
	private StockDto stock2;
	private List<StockDto> stocks;

	@BeforeEach
	public void beforeEach() {
		this.stocks = new ArrayList<>();

		this.stock1 = new StockDto("vale5", "Vale do Rio Doce PN");
		this.stock2 = new StockDto("petr4", "Petrobras PN");

		stocks.add(stock1);
		stocks.add(stock2);
	}

	@Test
	public void shouldCreateNewQuoteOperation() throws Exception {

		Mockito.when(stockService.findById("petr4")).thenReturn(stock2);

		JSONObject quotes = new JSONObject();
		quotes.put("2021-08-01", "10");
		quotes.put("2021-08-02", "14");
		quotes.put("2021-08-03", "11");

		JSONObject body = new JSONObject();
		body.put("id", "petr4");
		body.put("quotes", quotes);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/quote").content(body.toString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(201))
				.andExpect(MockMvcResultMatchers.jsonPath("$.stockId").value("petr4"))
				.andExpect(MockMvcResultMatchers.content().string(containsString("quotes")));
	}

	@Test
	public void shouldNotCreateNewQuoteOperation() throws Exception {

		Mockito.when(stockService.findById("testId")).thenReturn(null);

		JSONObject quotes = new JSONObject();
		quotes.put("2021-08-01", "10");
		quotes.put("2021-08-02", "14");
		quotes.put("2021-08-03", "11");

		JSONObject body = new JSONObject();
		body.put("id", "testId");
		body.put("quotes", quotes);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/quote").content(body.toString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(404))
				.andExpect(MockMvcResultMatchers.jsonPath("$.field").value("id"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Could not find a stock with id: testId"));
	}

	@Test
	public void shouldListByStockId() throws Exception {

		Mockito.when(stockService.findById("petr4")).thenReturn(stock2);

		mockMvc.perform(MockMvcRequestBuilders.get("/quote/petr4").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].stockId").value("petr4"));
	}

	@Test
	public void shouldNotListByStockId() throws Exception {

		Mockito.when(stockService.findById("testId")).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.get("/quote/testId").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(404))
				.andExpect(MockMvcResultMatchers.jsonPath("$.field").value("id"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Could not find a stock with id: testId"));
	}

	@Test
	public void shouldListAll() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/quote").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(1)));
	}

}
