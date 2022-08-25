package com.volatilitysurf.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
//import org.springframework.web.bind.annotation.GetMapping;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.volatilitysurf.models.Stock;
//Not a real controller! Just a code building grounds...
public class TestController {
	//this holds how we pull out expiration dates!
	public String callToMboum(
			@RequestParam("symbol") String symbol,
			HttpSession session) 
			throws UnsupportedEncodingException {
		if(symbol.length() > 4 || symbol.trim().length() < 2) {
			return "redirect:/";
		}
		symbol = symbol.toUpperCase();

		String host = "https://mboum-finance.p.rapidapi.com/op/option";
		String charset = "UTF-8";
		String x_rapidapi_host = "mboum-finance.p.rapidapi.com";
		String x_rapidapi_key = "ec2d471e81mshd1d781daa45de8ap15487djsn98d072fb2757";
		String query = String.format("symbol=%s", URLEncoder.encode(symbol, charset));
		String url = host + "?" + query;
		
		HttpResponse<JsonNode> response;
		try { response = Unirest.get(url)
					.header("X-RapidAPI-Key", x_rapidapi_key)
					.header("X-RapidAPI-Host", x_rapidapi_host)
					.asJson();
				if(response == null) {
					return "redirect:/";
				}
				JSONObject result = response.getBody().getObject().getJSONObject("optionChain").getJSONArray("result").getJSONObject(0);
				//Collect array of expirations per ticker
				JSONArray expiries = result.getJSONArray("expirationDates");
				session.setAttribute("expiries", expiries);
				JSONObject quote = result.getJSONObject("quote");
//				Stock ticker = stockServ.addStock(quote);
				
				JSONObject options = result.getJSONArray("options").getJSONObject(0);
//				optionServ.saveOptions(options, ticker);
				
//				ticker.setOptions(optionServ.getOptionsByStock(ticker));

//				session.setAttribute("ticker", ticker);
				return "redirect:/options";
		} catch (UnirestException e) {
			System.out.printf("get: %s", e.getMessage());
		}
		return "redirect:/";
	}
	public Stock fetchMoreOptionsByExpiry(HttpSession session)
							throws UnsupportedEncodingException {
		//Unpacking list of expirations from session
		JSONArray expiries = (JSONArray) session.getAttribute("expiries");
		//Unpacking list of ticker Stock object from session
		Stock ticker = (Stock) session.getAttribute("ticker");
		String symbol = ticker.getSymbol();
		String host = "https://mboum-finance.p.rapidapi.com/op/option";
		String charset = "UTF-8";
		String x_rapidapi_host = "mboum-finance.p.rapidapi.com";
		String x_rapidapi_key = "ec2d471e81mshd1d781daa45de8ap15487djsn98d072fb2757";
		for(int i = 1; i < expiries.length(); i++) {
			String expiry = expiries.getString(i);
			String query = String.format("expiration=%s&symbol=%s", URLEncoder.encode(expiry, charset),URLEncoder.encode(symbol, charset));
			String url = host + "?" + query;
			HttpResponse<JsonNode> response;
			try { response = Unirest.get(url)
					.header("X-RapidAPI-Key", x_rapidapi_key)
					.header("X-RapidAPI-Host", x_rapidapi_host)
					.asJson();
				//Contain the options object
				JSONObject options = response.getBody().getObject().getJSONObject("optionChain").getJSONArray("result").getJSONObject(0).getJSONArray("options").getJSONObject(0);
				//Send to options service for saving
//				optionServ.saveOptions(options, ticker);
				//optionServ not loaded here ^^ commented out to avoid errors
			} catch (UnirestException e) {
				System.out.printf("get: %s", e.getMessage());
			}
			
		}
//		ticker.setOptions(optionServ.getOptionsByStock(ticker));
		//Commented out ^^^ until broken up for optionServ access
		return ticker; //something? the stock object? 
	}
}
