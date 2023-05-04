package com.scalable.exchange.rate.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scalable.exchange.rate.exception.ExchangeRateException;
import com.scalable.exchange.rate.model.ConvertorModel;
import com.scalable.exchange.rate.model.ExchangeRateModel;
import com.scalable.exchange.rate.model.SupportedCurrenciesModel;
import com.scalable.exchange.rate.service.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ExchangeRateController {

	private static Logger log = LoggerFactory.getLogger(ExchangeRateController.class);
	
	private @Value("#{${published.exchange.rates.mapping}}") Map<String, String> publishedExchangeRatesMap;
	@Autowired
	private ExchangeRateService exchangeRateService;

	/**
	 * Api to fetch ECB reference rate and exchange rate for other pairs
	 * 
	 * @param currencyPair
	 * @return
	 * @throws ExchangeRateException
	 */
	@GetMapping("/api/exchangerate")
	public ResponseEntity<Object> getExchangeRates(@RequestParam(required = true) String currencyPair)
			throws ExchangeRateException {
		log.info("inside  getExchangeRates() method of ExchangeRateController for currency pair {}", currencyPair);
		ExchangeRateModel rateModel = exchangeRateService.getExchangeRates(currencyPair, publishedExchangeRatesMap);
		return new ResponseEntity<>(rateModel, HttpStatus.OK);

	}
	
	/**
	 * Api to fetch  list of supported currency  along with  count 
	 * 
	 * @return
	 * @throws ExchangeRateException
	 */

	@GetMapping("/api/supportedcurrencies")
	public ResponseEntity<Object> getListOfSupportedCurrenciesAndRequestCount() throws ExchangeRateException {
		log.info("inside getListOfSupportedCurrenciesAndRequestCount() method of ExchangeRateController");
		SupportedCurrenciesModel supportedCurrenciesModel = exchangeRateService
				.getSupportedCurrenciesAndRequestCount(publishedExchangeRatesMap);
		return new ResponseEntity<>(supportedCurrenciesModel, HttpStatus.OK);

	}

	/**
	 * Api to convert any amount in given currency to other currency
	 * 
	 * @param amount
	 * @param currency
	 * @param conversionCurrency
	 * @return
	 * @throws ExchangeRateException
	 */
	
	@GetMapping("/api/currencyconvertor")
	public ResponseEntity<Object> currencyConvertor(@RequestParam(required = true) String amount,
			@RequestParam(required = true) String currency, @RequestParam(required = true) String conversionCurrency)
			throws ExchangeRateException {
		log.info("inside currencyConvertor() method of ExchangeRateController");
		ConvertorModel convertorModel = exchangeRateService.currencyConvertor(amount, currency, conversionCurrency,
				publishedExchangeRatesMap);
		return new ResponseEntity<>(convertorModel, HttpStatus.OK);

	}
}
