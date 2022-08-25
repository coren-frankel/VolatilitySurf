package com.volatilitysurf.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

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

public class TestController {
	//Alternative API -- Not in use
//	public void retrieveTwelveDates(HttpSession session) throws UnsupportedEncodingException {
//		Stock ticker = (Stock) session.getAttribute("ticker");
//		String symbol = ticker.getSymbol();
//		String host1 = "https://twelve-data1.p.rapidapi.com/options/expiration?";
//		String charset = "UTF-8";
//		String x_rapidapi_key = "ec2d471e81mshd1d781daa45de8ap15487djsn98d072fb2757";
//		String x_rapidapi_host = "twelve-data1.p.rapidapi.com";
//		String query1 = String.format("symbol=%s", URLEncoder.encode(symbol, charset));
//		String url1 = host1 + query1;//Change??
//		//CALL TO TWELVE API USING TICKER SYMBOL TO GATHER OPTION EXPIRATION DATES
//		HttpResponse<JsonNode> response1;
//		try { response1 = Unirest.get(url1)
//				.header("X-RapidAPI-Key", x_rapidapi_key)
//				.header("X-RapidAPI-Host", x_rapidapi_host)
//				.asJson();
//			String status = response1.getBody().getObject().getString("status");
//			if(status == "error") {
//				System.out.println("Error getting request...");
//				return;
//			}
//			JSONArray result = response1.getBody().getObject().getJSONArray("dates");
//			ArrayList<String> dates = new ArrayList<String>();
//			for(int i = 0; i < result.length();i++) {
//				
//				
//			}
//		} catch (UnirestException e) {
//			System.out.printf("get: %s", e.getMessage());
//		}
//	}
	//pull out dates!
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
				JSONArray expiries = result.getJSONArray("expirationDates");
				session.setAttribute("expiries", expiries);
				JSONObject quote = result.getJSONObject("quote");
				Stock ticker = stockServ.addStock(quote);
				
				JSONObject options = result.getJSONArray("options").getJSONObject(0);
				optionServ.saveOptions(options, ticker);
				
				ticker.setOptions(optionServ.getOptionsByStock(ticker));

				session.setAttribute("ticker", ticker);
				return "redirect:/options";
		} catch (UnirestException e) {
			System.out.printf("get: %s", e.getMessage());
		}
		return "redirect:/";
	}
	public void getMoreOptionsByExpiry(HttpSession session) {
		JSONArray expiries = (JSONArray) session.getAttribute("expiries");
		
	}
}
