package com.volatilitysurf.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name="stocks")
public class Stock {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
	@NotBlank
	@Column(unique=true)
	private String symbol;	

	private String shortName;
	private String quoteSourceName;
	private String fullExchangeName;
	private String currency;
	private Double regularMarketPrice;
	private Double regularMarketChange;
	private Double regularMarketChangePercent;
	private Timestamp regularMarketTime;
	private String exchangeTimezoneShortName;
	
	@OneToMany(mappedBy="stock", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Option> options;
	
	public Stock() {}

	// create 3 strings with x-y-z data for implied volatility scatter plot 
	public ArrayList<String> getPlotData(char type){
		ArrayList<String> plotData = new ArrayList<String>();
		
		if (this.options.size() == 0) {
			plotData.add("[]");
			plotData.add("[]");
			plotData.add("[]");
			return plotData;
		}
		String xdata = "[";
		String ydata = "[";
		String zdata = "[";
		for(Option option: this.options) {
			Double moneyness = (Double) (option.getStrike() / this.regularMarketPrice);
			Integer expiry = option.getDaysToExpiration();
			String optionType = option.getOptionType(); 
			Integer daysSinceLastTrade = option.getDaysSinceLastTrade(); 
			if (optionType.charAt(0) == type 
					&& moneyness > 0.7 && moneyness < 1.4 
					&& expiry > 4
					&& daysSinceLastTrade < 3 ) {
				xdata = xdata +  moneyness.toString() + ",";
				ydata = ydata + expiry.toString() + ",";
				zdata = zdata + ((Double) (option.getImpliedVolatility() * 100)).toString() + ",";				
			}
		}		
		xdata = xdata + "]";
		ydata = ydata + "]";
		zdata = zdata + "]";
		
		plotData.add(xdata);
		plotData.add(ydata);
		plotData.add(zdata);

		return plotData;
	}
	
	// getters/setters
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

	public Double getRegularMarketPrice() {
		return regularMarketPrice;
	}

	public void setRegularMarketPrice(Double regularMarketPrice) {
		this.regularMarketPrice = regularMarketPrice;
	}

	public Double getRegularMarketChange() {
		return regularMarketChange;
	}

	public void setRegularMarketChange(Double regularMarketChange) {
		this.regularMarketChange = regularMarketChange;
	}

	public Double getRegularMarketChangePercent() {
		return regularMarketChangePercent;
	}

	public void setRegularMarketChangePercent(Double regularMarketChangePercent) {
		this.regularMarketChangePercent = regularMarketChangePercent;
	}

	public Timestamp getRegularMarketTime() {
		return regularMarketTime;
	}

	public void setRegularMarketTime(Timestamp regularMarketTime) {
		this.regularMarketTime = regularMarketTime;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}
	
	public String getExchangeTimezoneShortName() {
		return exchangeTimezoneShortName;
	}

	public void setExchangeTimezoneShortName(String exchangeTimezoneShortName) {
		this.exchangeTimezoneShortName = exchangeTimezoneShortName;
	}
}