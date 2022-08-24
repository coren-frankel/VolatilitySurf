package com.volatilitysurf.services;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.volatilitysurf.models.Option;
import com.volatilitysurf.models.Stock;
import com.volatilitysurf.repositories.OptionRepository;
import com.volatilitysurf.repositories.StockRepository;

@Service
public class StockService {
	@Autowired
	private StockRepository stockRepo;
	@Autowired
	private OptionService optionServ;
	//Call optRepo to save the options
	
	
	public Stock addStock(JSONObject r) {
		JSONObject ticker = r.getJSONObject("quote");
		JSONObject options = r.getJSONArray("options").getJSONObject(0);
		JSONArray calls = options.getJSONArray("calls");
		JSONArray puts = options.getJSONArray("puts");
		//Split the calls and puts? what, other than their contractSymbol, will we compare by?
		
		Stock s = new Stock();
		
		s.setSymbol(ticker.getString("symbol"));
		s.setShortName(ticker.getString("shortName"));
		s.setQuoteSourceName(ticker.getString("quoteSourceName"));
		s.setFullExchangeName(ticker.getString("fullExchangeName"));
		s.setCurrency(ticker.getString("currency"));
		s.setRegularMarketPrice(ticker.getDouble("regularMarketPrice"));
		s.setRegularMarketChange(ticker.getDouble("regularMarketChange"));
		s.setRegularMarketChangePercent(ticker.getDouble("regularMarketChangePercent"));
		Timestamp rmt = new Timestamp(ticker.getLong("regularMarketTime") * 1000);
		s.setRegularMarketTime(rmt);
		
		Stock newStock = stockRepo.save(s);
		
		for(int i = 0; i < calls.length(); i++) {
			JSONObject call = calls.getJSONObject(i);
			optionServ.saveNewOption(call, newStock, "C");
		}
		for(int i = 0; i < puts.length(); i++) {
			JSONObject put = puts.getJSONObject(i);
			optionServ.saveNewOption(put, newStock, "P");
		}
		return newStock;
	}
	
	public Stock updateStock(Stock s) {
		return stockRepo.save(s);//There's no way this is sufficient updating yet
	}
}
