package br.inatel.icc.quotationmanagement.dto;

public class StockDto {

	String id;
	String description;

	public StockDto(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

}
