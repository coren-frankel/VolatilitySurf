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

@Service
public class OptionService {
	
	@Autowired
	OptionRepository optRepo;

	public void saveOptions(JSONObject options, Stock stock) {
		JSONArray calls = options.getJSONArray("calls");
		JSONArray puts = options.getJSONArray("puts");
		
		for(int i = 0; i < calls.length(); i++) {
			JSONObject call = calls.getJSONObject(i);
			this.saveNewOption(call, stock, "C");
		}
		for(int i = 0; i < puts.length(); i++) {
			JSONObject put = puts.getJSONObject(i);
			this.saveNewOption(put, stock, "P");
		}
	}
	
	private Option saveNewOption(JSONObject JSONoption, Stock stock, String optionType) {
		Option option = new Option();
		
		option.setStock(stock);
		option.setOptionType(optionType);

		option.setContractSymbol(JSONoption.getString("contractSymbol"));
		option.setContractSize(JSONoption.getString("contractSize"));
		Timestamp expiration = new Timestamp(JSONoption.getLong("expiration") * 1000);
		Date expirationDate = new Date(expiration.getTime());
		option.setExpiration(expirationDate);
		Timestamp lastTradeDate = new Timestamp(JSONoption.getLong("lastTradeDate") * 1000);
		option.setLastTradeDate(lastTradeDate);
		option.setStrike(JSONoption.getDouble("strike"));
		option.setLastPrice(JSONoption.getDouble("lastPrice"));
		if(JSONoption.has("bid")) {			
			option.setBid(JSONoption.getDouble("bid"));
		} else {
			option.setBid(null);
		}
		if(JSONoption.has("ask")) {
			option.setAsk(JSONoption.getDouble("ask"));
		} else {
			option.setAsk(null);			
		}
		option.setCurrency(JSONoption.getString("currency"));
		option.setAbsoluteChange(JSONoption.getDouble("change"));
		if(JSONoption.has("percentChange")) {
			option.setPercentChange(JSONoption.getDouble("percentChange"));			
		} else {
			option.setPercentChange(null);			
		}
		if(JSONoption.has("volume")) {
			option.setVolume(JSONoption.getInt("volume"));    			
		} else {
			option.setVolume(null);
		}
		option.setOpenInterest(JSONoption.getInt("openInterest"));
		option.setImpliedVolatility(JSONoption.getDouble("impliedVolatility"));
		option.setInTheMoney(JSONoption.getBoolean("inTheMoney"));
		
		return optRepo.save(option);
	}
}
