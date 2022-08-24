package com.volatilitysurf.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
//import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Controller
public class MainController {
	
	
	@GetMapping("/")
	public String home() {
		return "index.jsp";
	}
	@PostMapping("/options")
	public String callToMboum(@RequestParam("symbol")String symbol,
								Model model) throws UnsupportedEncodingException {
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
//				String shortName = response.getBody().get("option")
				System.out.println(response);
//				System.out.println(shortName);
		} catch (UnirestException e) {
			System.out.printf("get: %s", e.getMessage());
		}
//		model.addAttribute("ticker", response);
		return "options.jsp";
	}
}
