package com.volatilitysurf.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
	OptionRepository optionRepo;
	
	public List<Option> getOptionsByStock(Stock stock){
		return optionRepo.findByStock(stock);
	}

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
		
		if(JSONoption.has("contractSize")) {
			option.setContractSize(JSONoption.getString("contractSize"));			
		} else {
			option.setContractSize(null);
		}
		if(JSONoption.has("expiration")) {
			Timestamp expiration = new Timestamp(JSONoption.getLong("expiration") * 1000);
			Date expirationDate = new Date(expiration.getTime());			
			option.setExpiration(expirationDate);
		} else {
			option.setExpiration(null);
		}
		if(JSONoption.has("lastTradeDate")) {
			Timestamp lastTradeDate = new Timestamp(JSONoption.getLong("lastTradeDate") * 1000);
			option.setLastTradeDate(lastTradeDate);			
		} else {
			option.setLastTradeDate(null);
		}
		if(JSONoption.has("strike")){
			option.setStrike(JSONoption.getDouble("strike"));			
		} else {
			option.setStrike(null);
		}
		if(JSONoption.has("lastPrice")) {
			option.setLastPrice(JSONoption.getDouble("lastPrice"));			
		} else {
			option.setLastPrice(null);
		}
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
		if(JSONoption.has("currency")) {
			option.setCurrency(JSONoption.getString("currency"));			
		} else {
			option.setCurrency(null);
		}
		if(JSONoption.has("change")) {
			option.setAbsoluteChange(JSONoption.getDouble("change"));			
		} else {
			option.setAbsoluteChange(null);
		}
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
		if(JSONoption.has("openInterest")) {
			option.setOpenInterest(JSONoption.getInt("openInterest"));			
		} else {
			option.setOpenInterest(null);
		}
		if(JSONoption.has("impliedVolatility")) {
			option.setImpliedVolatility(JSONoption.getDouble("impliedVolatility"));			
		} else {
			option.setImpliedVolatility(null);
		}
		if(JSONoption.has("inTheMoney")) {
			option.setInTheMoney(JSONoption.getBoolean("inTheMoney"));			
		} else {
			option.setInTheMoney(null);
		}
		
		return optionRepo.save(option);
	}
}
