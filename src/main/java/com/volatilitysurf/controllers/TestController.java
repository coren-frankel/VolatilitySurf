package com.volatilitysurf.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
//import org.springframework.web.bind.annotation.GetMapping;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.volatilitysurf.models.Stock;

public class TestController {
	
	public void retrieveTwelveDates(HttpSession session) throws UnsupportedEncodingException {
		Stock ticker = (Stock) session.getAttribute("ticker");
		String symbol = ticker.getSymbol();
		String host1 = "https://twelve-data1.p.rapidapi.com/options/expiration?";
		String charset = "UTF-8";
		String x_rapidapi_key = "ec2d471e81mshd1d781daa45de8ap15487djsn98d072fb2757";
		String x_rapidapi_host = "twelve-data1.p.rapidapi.com";
		String query1 = String.format("symbol=%s", URLEncoder.encode(symbol, charset));
		String url1 = host1 + query1;//Change??
		//CALL TO TWELVE API USING TICKER SYMBOL TO GATHER OPTION EXPIRATION DATES
		HttpResponse<JsonNode> response1;
		try { response1 = Unirest.get(url1)
				.header("X-RapidAPI-Key", x_rapidapi_key)
				.header("X-RapidAPI-Host", x_rapidapi_host)
				.asJson();
			String status = response1.getBody().getObject().getString("status");
			if(status == "error") {
				System.out.println("Error getting request...");
				return;
			}
			JSONArray result = response1.getBody().getObject().getJSONArray("dates");
			ArrayList<String> dates = new ArrayList<String>();
			for(int i = 0; i < result.length();i++) {
				
				
			}
		} catch (UnirestException e) {
			System.out.printf("get: %s", e.getMessage());
		}
		
		
		
	}
}
