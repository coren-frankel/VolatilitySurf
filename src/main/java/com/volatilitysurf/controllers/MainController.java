package com.volatilitysurf.controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.volatilitysurf.models.Option;
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
		Stock existingStock = stockServ.getStockBySymbol(symbol);
		if(existingStock != null) {
			stockServ.deleteStock(existingStock);			
		}
		
		JSONArray expirationDates = result.getJSONArray("expirationDates");
		JSONObject quote = result.getJSONObject("quote");
		JSONObject options = result.getJSONArray("options").getJSONObject(0);
		
		Stock ticker = stockServ.addStock(quote);
		
		// fetchStockData returns options for the first available expirationDate
		// therefore the loop below starts at i = 1
		optionServ.saveOptions(ticker, options);
		for(int i = 1; i < expirationDates.length(); i++) {
			Long expiry = expirationDates.getLong(i);
			options = optionServ.fetchOptionData(ticker, expiry.toString());	
			optionServ.saveOptions(ticker, options);
		}
		
		ticker.setOptions(optionServ.getOptionsByStock(ticker));
		session.setAttribute("symbol",symbol);
		session.setAttribute("expirations", expirationDates);
		return "redirect:/options";
	}
		
	@GetMapping("/options")
	public String options(HttpSession session, Model model) {
		//Render Stock Data w/ List of options
		String symbol = (String)session.getAttribute("symbol");
		List<Date> expirations = (List<Date>)session.getAttribute("expirations");
		Date expiry = (Date)session.getAttribute("expiry");
		if(symbol == null) {
			return "redirect:/";
		} else {
			if(expiry == null) {//IF THE EXPIRY CHOICE HAS NOT BEEN MADE IN SESSION, 
				session.setAttribute("expiry", expirations.get(0));
			}//START WITH THE FIRST FROM THE ARRAYLIST
			Stock ticker = stockServ.getStockBySymbol(symbol);
			model.addAttribute("ticker", ticker);
//			model.addAttribute("expirations", expirations);
			List<Option> options = optionServ.getOptionsByExpiration(ticker, expiry); 
			model.addAttribute("options", options);
		}
		return "options.jsp";
	}
	
	@PostMapping("/setOptionsExpiry")
	public String setExpiry(HttpSession session, 
			@RequestParam("expiryDate") Date expiry) {
		session.setAttribute("expiry", expiry);
		return "redirect:/options";
	}
	
	@GetMapping("/volsurf")//rendering current "surface" here with default AAPL
	public String showVolSurf(HttpSession session, Model model) {
		String symbol = (String)session.getAttribute("symbol");
		System.out.println(symbol);
		if(symbol == null) {
			return "redirect:/";
		} else {
			Stock ticker = stockServ.getStockBySymbol(symbol);
			ArrayList<String> plotData = ticker.getPlotData();
			System.out.println(plotData.get(0));
			System.out.println(plotData.get(1));
			System.out.println(plotData.get(2));
			model.addAttribute("ticker", ticker);
			model.addAttribute("xdata", plotData.get(0));
			model.addAttribute("ydata", plotData.get(1));
			model.addAttribute("zdata", plotData.get(2));
		}
		return "volsurf.jsp";
	}
	@GetMapping("/test")//Rendering New possibilities here with default AAPL
	public String volSurfTest(Model model) {//Volsurf testing
		Stock testTicker = stockServ.getStockBySymbol("AAPL");
		model.addAttribute("ticker", testTicker);
//		model.setAttribute('plotData',plot);
		return "test.jsp";
	}
	@GetMapping("/test/options")//with default AAPL options
	public String optTest(Model model) {
		Stock testTicker = stockServ.getStockBySymbol("AAPL");
		model.addAttribute("ticker", testTicker);
		return "options.jsp";
	}
}
