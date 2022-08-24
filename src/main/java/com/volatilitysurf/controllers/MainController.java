package com.volatilitysurf.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.volatilitysurf.models.Stock;
import com.volatilitysurf.services.StockService;
//import com.volatilitysurf.models.Option;

@Controller
public class MainController {
	@Autowired
	private StockService stockServ;
	//One Service should suffice... the options are saved in relation to a stock.
	
	@GetMapping("/")
	public String home(HttpSession session) {
		session.invalidate();//Clear session, if it was used, no problem?
		return "index.jsp";
	}
	@PostMapping("/process")
	public String callToMboum(@RequestParam("symbol")String symbol,
								HttpSession session) throws UnsupportedEncodingException {
		if(symbol.length() > 4 || symbol.trim().length() < 2) {
			return "redirect:/";
		}//Small validation of submission length
		String host = "https://mboum-finance.p.rapidapi.com/op/option";
		String charset = "UTF-8";
		String x_rapidapi_host = "mboum-finance.p.rapidapi.com";
		String x_rapidapi_key = "ec2d471e81mshd1d781daa45de8ap15487djsn98d072fb2757";
		symbol = symbol.toUpperCase();
		String query = String.format("symbol=%s", URLEncoder.encode(symbol, charset));
		String url = host + "?" + query;
		HttpResponse<JsonNode> response;
		try { response = Unirest.get(url)
					.header("X-RapidAPI-Key", x_rapidapi_key)
					.header("X-RapidAPI-Host", x_rapidapi_host)
					.asJson();
				if(response == null) {//If there isn't a response then go home.
					return "redirect:/";
				}
				JSONObject result = response.getBody().getObject().getJSONObject("optionChain").getJSONArray("result").getJSONObject(0);
				Stock ticker = stockServ.addStock(result);//off to Process the stock!
				session.setAttribute("ticker", ticker);//Save new stock to current session
				return "redirect:/options";
//				Redirect coming soon
//				return "redirect:/options";
		} catch (UnirestException e) {
			System.out.printf("get: %s", e.getMessage());
		}//Else return to blank page
		return "redirect:/";
	}
	@GetMapping("/options")
	public String options(HttpSession session) {
		//Render Stock Data w/ List of options
		return "options.jsp";
	}
}
