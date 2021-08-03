package br.inatel.icc.quotationmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "quotes")
@NoArgsConstructor
public class Quote {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private LocalDate date;
	private BigDecimal price;

	@ManyToOne
	private StockOperation stockOperation;

	public Quote(LocalDate date, BigDecimal price, StockOperation stockOperation) {
		this.date = date;
		this.price = price;
		this.stockOperation = stockOperation;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public StockOperation getStockOperation() {
		return stockOperation;
	}

}
