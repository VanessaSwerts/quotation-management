package br.inatel.icc.quotationmanagement.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class StockOperation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private UUID uuid;

	private String stockId;

	@OneToMany(mappedBy = "stockOperation", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Quote> quotes = new ArrayList<>();

	public StockOperation(String stockId) {
		this.stockId = stockId;
		this.uuid = UUID.randomUUID();
	}

	public UUID getUUID() {
		return uuid;
	}
	
	public Long getId() {
		return id;
	}

	public String getStockId() {
		return stockId;
	}

	public List<Quote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}

}
