package com.volatilitysurf.services;

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
		Stock newStock = stockRepo.save(s);//Save new Stock
		for(int i = 0; i < calls.length(); i++) {//Save each option
			JSONObject each = calls.getJSONObject(i);
			Option o = new Option();
			o.setContractSymbol(each.getString("contractSymbol"));
			o.setContractSize(each.getString("contractSize"));
			o.setExpiration(each.getInt("expiration"));
			o.setLastTradeDate(each.getInt("lastTradeDate"));
			o.setStrike(each.getDouble("strike"));
			o.setLastPrice(each.getDouble("lastPrice"));
			o.setBid(each.getDouble("bid"));
			o.setAsk(each.getDouble("ask"));
			o.setCurrency(each.getString("currency"));
			o.setAbsoluteChange(each.getDouble("change"));
			o.setPercentChange(each.getDouble("percentChange"));
			o.setVolume(each.getInt("volume"));
			o.setOpenInterest(each.getInt("openInterest"));
			o.setImpliedVolatility(each.getDouble("impliedVolatility"));
			o.setStock(s);
			optRepo.save(o);
		}
		return newStock;
	}
	
	public Stock updateStock(Stock s) {
		return stockRepo.save(s);//There's no way this is sufficient updating yet
	}
}
