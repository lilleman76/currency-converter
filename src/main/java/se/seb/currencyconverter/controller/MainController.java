package se.seb.currencyconverter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.seb.currencyconverter.service.CurrencyConverterService;

import javax.inject.Inject;

@Controller
public class MainController {
	private final CurrencyConverterService currencyConverterService;

	@Inject
	public MainController(CurrencyConverterService currencyConverterService) {
		this.currencyConverterService = currencyConverterService;
	}

	@GetMapping("/main")
	public String main(Model model) {
		model.addAttribute("currencies", currencyConverterService.getTodaysCurrencies());
		return "main";
	}

}
