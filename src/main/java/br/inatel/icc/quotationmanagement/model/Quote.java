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
	private StockOperation stock;

	public Quote(LocalDate date, BigDecimal price, StockOperation stock) {
		this.date = date;
		this.price = price;
		this.stock = stock;
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

	public StockOperation getStock() {
		return stock;
	}

}
