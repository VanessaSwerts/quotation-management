package br.inatel.icc.quotationmanagement.acceptance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.inatel.icc.quotationmanagement.QuotationManagementApplication;
import br.inatel.icc.quotationmanagement.service.StockService;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = QuotationManagementApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CucumberSpringConfiguration {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	@Autowired
	protected StockService stockService;
}
