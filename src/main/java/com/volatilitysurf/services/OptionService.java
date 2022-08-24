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

	public Option saveNewOption(JSONObject JSONoption, Stock stock) {
		Option o = new Option();
		
		o.setContractSymbol(JSONoption.getString("contractSymbol"));
		o.setContractSize(JSONoption.getString("contractSize"));
		Timestamp exp = new Timestamp(JSONoption.getLong("expiration") * 1000);
		Date expDate = new Date(exp.getTime());
		o.setExpiration(expDate);
		Timestamp ltd = new Timestamp(JSONoption.getLong("lastTradeDate") * 1000);
		o.setLastTradeDate(ltd);
		o.setStrike(JSONoption.getDouble("strike"));
		o.setLastPrice(JSONoption.getDouble("lastPrice"));
		o.setBid(JSONoption.getDouble("bid"));
		o.setAsk(JSONoption.getDouble("ask"));
		o.setCurrency(JSONoption.getString("currency"));
		o.setAbsoluteChange(JSONoption.getDouble("change"));
		o.setPercentChange(JSONoption.getDouble("percentChange"));
		//o.setVolume(JSONoption.getInt("volume"));    // "volume" often not found
		o.setOpenInterest(JSONoption.getInt("openInterest"));
		o.setImpliedVolatility(JSONoption.getDouble("impliedVolatility"));
		
		o.setStock(stock);
		
		return optRepo.save(o);
	}
	
}
