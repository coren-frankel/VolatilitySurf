package com.volatilitysurf.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.volatilitysurf.models.Option;
import com.volatilitysurf.models.Stock;
import com.volatilitysurf.repositories.OptionRepository;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class OptionService {
	
	@Autowired
	OptionRepository optionRepo;
	
	//DOTENV-JAVA PACKAGE PERHAPS ONLY NECESSARY IN DEV ENVIRONMENT
	Dotenv dotenv = Dotenv.configure()
	        .ignoreIfMissing()
	        .load();
	private String apiKey = dotenv.get("MBOUM_KEY");
	
	public List<Option> getOptionsByStock(Stock stock){
		return optionRepo.findByStock(stock);
	}
	//FILTER OPTIONS BY EXPIRATION OF CONTRACTS
	public List<Option> getOptionsByExpiration(Stock stock, Date expiration){
		return optionRepo.findByStockAndExpiration(stock, expiration);
	}
	
	public JSONObject fetchOptionData(Stock stock, String expiry)
			throws UnsupportedEncodingException	{
		String host = "https://mboum-finance.p.rapidapi.com/op/option";
		String charset = "UTF-8";
		String x_rapidapi_host = "mboum-finance.p.rapidapi.com";
		String x_rapidapi_key = apiKey;
		String query = String.format("expiration=%s&symbol=%s", URLEncoder.encode(expiry, charset),URLEncoder.encode(stock.getSymbol(), charset));
		String url = host + "?" + query;
		
		HttpResponse<JsonNode> response;
		
		try { response = Unirest.get(url)
				.header("X-RapidAPI-Key", x_rapidapi_key)
				.header("X-RapidAPI-Host", x_rapidapi_host)
				.asJson();	
			JSONObject options = response.getBody().getObject().getJSONObject("optionChain").getJSONArray("result").getJSONObject(0).getJSONArray("options").getJSONObject(0);	
			return options;
		} 
		catch (UnirestException e) {
			System.out.printf("get: %s", e.getMessage());
			return null;
		}
	}

	public void saveOptions(Stock stock, JSONObject options) {
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
