package br.inatel.icc.quotationmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "quotes")
@NoArgsConstructor
public class Quote {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

	private String stockId;
	private LocalDate date;
	private BigDecimal price;

	public Quote(String stockId, LocalDate date, BigDecimal price) {
		this.stockId = stockId;
		this.date = date;
		this.price = price;
	}

	public UUID getId() {
		return id;
	}

	public String getStockId() {
		return stockId;
	}

	public LocalDate getDate() {
		return date;
	}

	public BigDecimal getPrice() {
		return price;
	}

}
