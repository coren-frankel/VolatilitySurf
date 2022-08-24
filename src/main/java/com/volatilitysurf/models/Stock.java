package com.volatilitysurf.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="stocks")
public class Stock {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
	@NotBlank
	private String symbol;	

	private String shortName;
	private String quoteSourceName;
	private String fullExchangeName;
	private String currency;
	private double regularMarketPrice;
	private double regularMarketChange;
	private double regularMarketChangePercent;
	private Date regularMarketTime;
	
	@OneToMany(mappedBy="stock", fetch=FetchType.LAZY)
	private List<Option> options;
	

	public Stock() {}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getQuoteSourceName() {
		return quoteSourceName;
	}

	public void setQuoteSourceName(String quoteSourceName) {
		this.quoteSourceName = quoteSourceName;
	}

	public String getFullExchangeName() {
		return fullExchangeName;
	}

	public void setFullExchangeName(String fullExchangeName) {
		this.fullExchangeName = fullExchangeName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getRegularMarketPrice() {
		return regularMarketPrice;
	}

	public void setRegularMarketPrice(double regularMarketPrice) {
		this.regularMarketPrice = regularMarketPrice;
	}

	public double getRegularMarketChange() {
		return regularMarketChange;
	}

	public void setRegularMarketChange(double regularMarketChange) {
		this.regularMarketChange = regularMarketChange;
	}

	public double getRegularMarketChangePercent() {
		return regularMarketChangePercent;
	}

	public void setRegularMarketChangePercent(double regularMarketChangePercent) {
		this.regularMarketChangePercent = regularMarketChangePercent;
	}

	public Date getRegularMarketTime() {
		return regularMarketTime;
	}

	public void setRegularMarketTime(Date regularMarketTime) {
		this.regularMarketTime = regularMarketTime;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}
}