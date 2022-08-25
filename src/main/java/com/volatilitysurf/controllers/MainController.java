package com.volatilitysurf.controllers;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.volatilitysurf.models.Stock;
import com.volatilitysurf.services.OptionService;
import com.volatilitysurf.services.StockService;

@Controller
public class MainController {
	@Autowired
	private StockService stockServ;
	
	@Autowired
	private OptionService optionServ;
	
	@GetMapping("/")
	public String home(HttpSession session) {
		session.invalidate();//Clear session, if it was used, no problem?
		return "index.jsp";
	}
	
	@PostMapping("/fetch")
	public String fetch(
			@RequestParam("symbol") String symbol,
			HttpSession session) 
			throws UnsupportedEncodingException {
		if(symbol.length() > 4 || symbol.trim().length() < 2) {
			return "redirect:/";
		}
		JSONObject result = stockServ.fetchStockData(symbol);
		
		if(result == null) {
			return "redirect:/";
		}
		
		JSONArray expirationDates = result.getJSONArray("expirationDates");
		
		JSONObject quote = result.getJSONObject("quote");
		Stock ticker = stockServ.addStock(quote);
		
		// fetchStockData returns options for the first available expirationDate
		// therefore the loop below starts at i = 1
		JSONObject options = result.getJSONArray("options").getJSONObject(0);
		optionServ.saveOptions(ticker, options);
		
		for(int i = 1; i < expirationDates.length(); i++) {
			Long expiry = expirationDates.getLong(i);
			options = optionServ.fetchOptionData(ticker, expiry.toString());	
			optionServ.saveOptions(ticker, options);
		}
		
		ticker.setOptions(optionServ.getOptionsByStock(ticker));

		session.setAttribute("ticker", ticker);
		return "redirect:/options";
	}
		
	@GetMapping("/options")
	public String options(HttpSession session) {
		//Render Stock Data w/ List of options
		return "options.jsp";
	}
}
