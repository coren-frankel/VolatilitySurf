package com.volatilitysurf.models;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="options")
public class Option {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(unique=true)
	private String contractSymbol;
	private String optionType; // "C" or "P" 
	private String contractSize;
	private Date expiration;
	private Timestamp lastTradeDate;
	private Double strike;
	private Double lastPrice;
	private Double bid;
	private Double ask;
	private String currency;
	private Double absoluteChange;
	private Double percentChange;
	private Integer volume;
	private Integer openInterest;
	private Double impliedVolatility;
	private Boolean inTheMoney;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="stock_id")
	private Stock stock;
	
	public Option() {}

	// number of calendar days until expiration
	public Integer getDaysToExpiration() {
		Date now = new Date();
		return (int) ((expiration.getTime() - now.getTime()) / (1000 * 60 * 60 * 24));
	}
	
	public Integer getDaysSinceLastTrade() {
		Date now = new Date();
		return (int) ((now.getTime() - lastTradeDate.getTime()) / (1000 * 60 * 60 * 24));
	}
	
	// getters/setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContractSymbol() {
		return contractSymbol;
	}

	public void setContractSymbol(String contractSymbol) {
		this.contractSymbol = contractSymbol;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public String getContractSize() {
		return contractSize;
	}

	public void setContractSize(String contractSize) {
		this.contractSize = contractSize;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public Timestamp getLastTradeDate() {
		return lastTradeDate;
	}

	public void setLastTradeDate(Timestamp lastTradeDate) {
		this.lastTradeDate = lastTradeDate;
	}

	public Double getStrike() {
		return strike;
	}

	public void setStrike(Double strike) {
		this.strike = strike;
	}

	public Double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public Double getBid() {
		return bid;
	}

	public void setBid(Double bid) {
		this.bid = bid;
	}

	public Double getAsk() {
		return ask;
	}

	public void setAsk(Double ask) {
		this.ask = ask;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAbsoluteChange() {
		return absoluteChange;
	}

	public void setAbsoluteChange(Double absoluteChange) {
		this.absoluteChange = absoluteChange;
	}

	public Double getPercentChange() {
		return percentChange;
	}

	public void setPercentChange(Double percentChange) {
		this.percentChange = percentChange;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Integer getOpenInterest() {
		return openInterest;
	}

	public void setOpenInterest(Integer openInterest) {
		this.openInterest = openInterest;
	}

	public Double getImpliedVolatility() {
		return impliedVolatility;
	}

	public void setImpliedVolatility(Double impliedVolatility) {
		this.impliedVolatility = impliedVolatility;
	}

	public Boolean getInTheMoney() {
		return inTheMoney;
	}

	public void setInTheMoney(Boolean inTheMoney) {
		this.inTheMoney = inTheMoney;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}	
}