package com.volatilitysurf.services;

import java.sql.Timestamp;
import java.util.Date;

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

	public Option saveNewOption(JSONObject JSONoption, Stock stock, String optionType) {
		Option o = new Option();
		
		o.setStock(stock);
		o.setOptionType(optionType);

		o.setContractSymbol(JSONoption.getString("contractSymbol"));
		o.setContractSize(JSONoption.getString("contractSize"));
		Timestamp expiration = new Timestamp(JSONoption.getLong("expiration") * 1000);
		Date expirationDate = new Date(expiration.getTime());
		o.setExpiration(expirationDate);
		Timestamp lastTradeDate = new Timestamp(JSONoption.getLong("lastTradeDate") * 1000);
		o.setLastTradeDate(lastTradeDate);
		o.setStrike(JSONoption.getDouble("strike"));
		o.setLastPrice(JSONoption.getDouble("lastPrice"));
		if(JSONoption.has("bid")) {			
			o.setBid(JSONoption.getDouble("bid"));
		} else {
			o.setBid(null);
		}
		if(JSONoption.has("ask")) {
			o.setAsk(JSONoption.getDouble("ask"));
		} else {
			o.setAsk(null);			
		}
		o.setCurrency(JSONoption.getString("currency"));
		o.setAbsoluteChange(JSONoption.getDouble("change"));
		if(JSONoption.has("percentChange")) {
			o.setPercentChange(JSONoption.getDouble("percentChange"));			
		} else {
			o.setPercentChange(null);			
		}
		if(JSONoption.has("volume")) {
			o.setVolume(JSONoption.getInt("volume"));    			
		} else {
			o.setVolume(null);
		}
		o.setOpenInterest(JSONoption.getInt("openInterest"));
		o.setImpliedVolatility(JSONoption.getDouble("impliedVolatility"));
		o.setInTheMoney(JSONoption.getBoolean("inTheMoney"));
		
		return optRepo.save(o);
	}
}
