package com.volatilitysurf.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.volatilitysurf.models.Stock;
import com.volatilitysurf.repositories.StockRepository;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class StockService {
	@Autowired
	private StockRepository stockRepo;
	
	//DOTENV-JAVA PACKAGE PERHAPS ONLY NECESSARY IN DEV ENVIRONMENT
	Dotenv dotenv = Dotenv.configure()
	        .ignoreIfMissing()
	        .load();
	private String apiKey = dotenv.get("MBOUM_KEY");
	
	public Stock getStockBySymbol(String symbol) {
		symbol = symbol.toUpperCase();
		Optional<Stock> stock = stockRepo.findBySymbol(symbol);
		if(stock.isPresent()) {
			return stock.get();
		} else {
			return null;
		}
	}
	public void deleteStock(Stock stock) {
		stockRepo.delete(stock);
	}
	public Date formatExpiration(Long idx) {
		Timestamp expiration = new Timestamp(idx * 1000);
		return new Date(expiration.getTime());
	}
		
	public JSONObject fetchStockData(String symbol) 
			throws UnsupportedEncodingException{
		symbol = symbol.toUpperCase();
		
		String host = "https://mboum-finance.p.rapidapi.com/op/option";
		String charset = "UTF-8";
		String x_rapidapi_host = "mboum-finance.p.rapidapi.com";
		String x_rapidapi_key = apiKey;
		String query = String.format("symbol=%s", URLEncoder.encode(symbol, charset));
		String url = host + "?" + query;
		
		HttpResponse<JsonNode> response;
		try { response = Unirest.get(url)
				.header("X-RapidAPI-Key", x_rapidapi_key)
				.header("X-RapidAPI-Host", x_rapidapi_host)
				.asJson();
				JSONArray result = response.getBody().getObject().getJSONObject("optionChain").getJSONArray("result");
				if(result.length() == 0) {//MBOUM RETURNS EMPTY "RESULT" ARRAY RATHER THAN ERROR MSG
					return null;//TESTING EXAMPLES LIKE "DORK" OR "OOZE" DON'T TRIGGER ERRORS
				}//NULL WILL TRIGGER FLASH MSG
				//ELSE SEND THE STOCK
				return result.getJSONObject(0);
		} 
		catch (UnirestException e) {
			System.out.printf("get: %s", e.getMessage());
			return null;
		}				
	}
	
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

}
