package com.volatilitysurf.models;

//import java.util.Date;

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
	
	private String contractSize;
	//Adapting expiration and lastTradeDate to int for JSON key:value extraction
	private int expiration;
	private int lastTradeDate;
	private double strike;
	private double lastPrice;
	private double bid;
	private double ask;
	private String currency;
	private double absoluteChange;
	private double percentChange;
	private int volume;
	private int openInterest;
	private double impliedVolatility;
	private boolean inTheMoney;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="stock_id")
	private Stock stock;
	
	public Option() {}

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

	public String getContractSize() {
		return contractSize;
	}

	public void setContractSize(String contractSize) {
		this.contractSize = contractSize;
	}

	public int getExpiration() {
		return expiration;
	}

	public void setExpiration(int expiration) {
		this.expiration = expiration;
	}

	public int getLastTradeDate() {
		return lastTradeDate;
	}

	public void setLastTradeDate(int lastTradeDate) {
		this.lastTradeDate = lastTradeDate;
	}

	public double getStrike() {
		return strike;
	}

	public void setStrike(double strike) {
		this.strike = strike;
	}

	public double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public double getBid() {
		return bid;
	}

	public void setBid(double bid) {
		this.bid = bid;
	}

	public double getAsk() {
		return ask;
	}

	public void setAsk(double ask) {
		this.ask = ask;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getAbsoluteChange() {
		return absoluteChange;
	}

	public void setAbsoluteChange(double absoluteChange) {
		this.absoluteChange = absoluteChange;
	}

	public double getPercentChange() {
		return percentChange;
	}

	public void setPercentChange(double percentChange) {
		this.percentChange = percentChange;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getOpenInterest() {
		return openInterest;
	}

	public void setOpenInterest(int openInterest) {
		this.openInterest = openInterest;
	}

	public double getImpliedVolatility() {
		return impliedVolatility;
	}

	public void setImpliedVolatility(double impliedVolatility) {
		this.impliedVolatility = impliedVolatility;
	}

	public boolean isInTheMoney() {
		return inTheMoney;
	}

	public void setInTheMoney(boolean inTheMoney) {
		this.inTheMoney = inTheMoney;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}
	
	
}