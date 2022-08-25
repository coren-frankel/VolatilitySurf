package com.volatilitysurf.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.volatilitysurf.models.Stock;

public class TestController {
	
	@GetMapping("/TestRoute")
	public void testTwelve(HttpSession session) throws UnsupportedEncodingException {
		Stock ticker = (Stock) session.getAttribute("ticker");
		String symbol = ticker.getSymbol();
		String host1 = "https://twelve-data1.p.rapidapi.com/options/expiration?";
		String host2 = "https://twelve-data1.p.rapidapi.com/options/";
		String charset = "UTF-8";
		String x_rapidapi_key = "ec2d471e81mshd1d781daa45de8ap15487djsn98d072fb2757";
		String x_rapidapi_host = "mboum-finance.p.rapidapi.com";//Change
		String query1 = String.format("symbol=%s", URLEncoder.encode(symbol, charset));
		String query2 = String.format("symbol=%s", URLEncoder.encode(symbol, charset));
		String url1 = host1 + query1;//Change??
		String url2 = host2 + "?" + query2;
		//CALL TO TWELVE API USING TICKER SYMBOL TO GATHER ~11 OPTION EXPIRATION DATES
		HttpResponse<JsonNode> response;
		try { response = Unirest.get(url1)
				.header("X-RapidAPI-Key", x_rapidapi_key)
				.header("X-RapidAPI-Host", x_rapidapi_host)
				.asJson();
			String status = response.getBody().getObject().getString("status");
			if(status == "error") {
				System.out.println("Error getting request...");
				return;
			}
			JSONArray result = response.getBody().getObject().getJSONArray("dates");
			int cap;
			if(result.length()>12) {
				cap = 12;
			}
			for(int each : result) {
				
			}
		} catch (UnirestException e) {
			System.out.printf("get: %s", e.getMessage());
		}
		
		//CALL TWELVE AGAIN TO GET STOCKS FROM SUBSEQUENT GATHERED EXPIRIES
		HttpResponse<String> response = Unirest.get("https://twelve-data1.p.rapidapi.com/options/chain?symbol=AAPL&expiration_date=2022-08-26")
				.header("X-RapidAPI-Key", "ec2d471e81mshd1d781daa45de8ap15487djsn98d072fb2757")
				.header("X-RapidAPI-Host", "twelve-data1.p.rapidapi.com")
				.asString();
		
	}
}
