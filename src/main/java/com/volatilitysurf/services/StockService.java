package com.volatilitysurf.services;

import java.util.ArrayList;

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
	private OptionRepository optRepo;
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
		s.setRegularMarketTime(ticker.getInt("regularMarketTime"));
		
		//Consider Revision **Can be revisited for puts, and need to decide if
		//forEach is the right way to unpack this package.
		//It seems that the JSONObject is more accessible 
		//than when we cast "each" as an Object with the forEach.
		//Try regular for loop
		for(Object each: calls) {
			Option o = new Option();
//			o.setContractSymbol(each.contractSymbol);
//			Throwing an error--loop needs work.
			
			
			
			
			
			
		}
		
		
//		return stockRepo.save(s);
		//not saving data yet...
		return s;
	}
	
	public Stock updateStock(Stock s) {
		return stockRepo.save(s);//There's no way this is sufficient updating
	}
}
