package br.inatel.icc.quotationmanagement.configs.initialization;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.inatel.icc.quotationmanagement.service.StockService;

@Component
public class InitializationApp {
	
	@Autowired
	private StockService stockService;

	@PostConstruct
	private void init() {
		stockService.registerNotification();
	}
}
