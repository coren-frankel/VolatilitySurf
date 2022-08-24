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
		stock.setShortName(quote.getString("shortName"));
		stock.setQuoteSourceName(quote.getString("quoteSourceName"));
		stock.setFullExchangeName(quote.getString("fullExchangeName"));
		stock.setCurrency(quote.getString("currency"));
		stock.setRegularMarketPrice(quote.getDouble("regularMarketPrice"));
		stock.setRegularMarketChange(quote.getDouble("regularMarketChange"));
		stock.setRegularMarketChangePercent(quote.getDouble("regularMarketChangePercent"));
		Timestamp rmt = new Timestamp(quote.getLong("regularMarketTime") * 1000);
		stock.setRegularMarketTime(rmt);
		stock.setExchangeTimezoneShortName(quote.getString("exchangeTimezoneShortName"));
		
		Stock newStock = stockRepo.save(stock);
		return newStock;
	}
	
	public Stock updateStock(Stock stock) {
		return stockRepo.save(stock);
	}
}
