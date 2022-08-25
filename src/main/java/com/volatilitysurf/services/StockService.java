package com.volatilitysurf.services;

import java.sql.Timestamp;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.volatilitysurf.models.Stock;
import com.volatilitysurf.repositories.StockRepository;

@Service
public class StockService {
	@Autowired
	private StockRepository stockRepo;
	
	public Stock addStock(JSONObject quote) {
		Stock stock = new Stock();
		
		stock.setSymbol(quote.getString("symbol"));
		
		if(quote.has("shortName")) {
			stock.setShortName(quote.getString("shortName"));			
		} else {
			stock.setShortName(null);
		}
		if(quote.has("quoteSourceName")) {
			stock.setQuoteSourceName(quote.getString("quoteSourceName"));			
		} else {
			stock.setQuoteSourceName(null);
		}
		if(quote.has("fullExchangeName")){			
			stock.setFullExchangeName(quote.getString("fullExchangeName"));
		} else {
			stock.setFullExchangeName(null);
		}
		if(quote.has("currency")) {
			stock.setCurrency(quote.getString("currency"));			
		} else {
			stock.setCurrency(null);
		}
		if(quote.has("regularMarketPrice")){			
			stock.setRegularMarketPrice(quote.getDouble("regularMarketPrice"));
		} else {
			stock.setRegularMarketPrice(null);
		}
		if(quote.has("regularMarketChange")) {
			stock.setRegularMarketChange(quote.getDouble("regularMarketChange"));			
		} else {
			stock.setRegularMarketChange(null);
		}
		if(quote.has("regularMarketChangePercent")) {
			stock.setRegularMarketChangePercent(quote.getDouble("regularMarketChangePercent"));			
		} else {
			stock.setRegularMarketChangePercent(null);
		}
		if(quote.has("regularMarketTime")) {
			Timestamp rmt = new Timestamp(quote.getLong("regularMarketTime") * 1000);
			stock.setRegularMarketTime(rmt);			
		} else {
			stock.setRegularMarketTime(null);
		}
		if(quote.has("exchangeTimezoneShortName")) {
			stock.setExchangeTimezoneShortName(quote.getString("exchangeTimezoneShortName"));			
		} else {
			stock.setExchangeTimezoneShortName(null);
		}
		
		Stock newStock = stockRepo.save(stock);
		return newStock;
	}
	
	public Stock updateStock(Stock stock) {
		return stockRepo.save(stock);
	}
}
