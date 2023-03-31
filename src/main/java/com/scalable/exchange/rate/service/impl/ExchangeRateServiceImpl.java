package com.scalable.exchange.rate.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.scalable.exchange.rate.constants.ExchangeRateConstants;
import com.scalable.exchange.rate.controller.ExchangeRateController;
import com.scalable.exchange.rate.exception.ExchangeRateException;
import com.scalable.exchange.rate.model.ConvertorModel;
import com.scalable.exchange.rate.model.ExchangeRateModel;
import com.scalable.exchange.rate.model.Rate;
import com.scalable.exchange.rate.model.SupportedCurrenciesModel;
import com.scalable.exchange.rate.model.SupportedCurrency;
import com.scalable.exchange.rate.service.ExchangeRateService;
import com.scalable.exchange.rate.util.ExchangeRateUtil;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
	private static Logger log = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

	private Map<String, Integer> viewCount;

	public ExchangeRateServiceImpl(Map<String, Integer> viewCount) {
		this.viewCount = new HashMap<String, Integer>();
	}

	@Override
	public ExchangeRateModel getExchangeRates(String currencyPair, Map<String, String> publishedExchangeRatesMap)
			throws ExchangeRateException {
		log.info("inside getExchangeRates() method of ExchangeRateServiceImpl");
		String[] currency = currencyPair.toUpperCase().split(ExchangeRateConstants.SEPARATOR);
		if (currency[0].equals(currency[1])) {
			throw new ExchangeRateException("Invalid Input", HttpStatus.BAD_REQUEST);
		}

		if ((!currency[0].equals(ExchangeRateConstants.EUR_CURRENCY)
				&& !publishedExchangeRatesMap.containsKey(currency[0]))
				|| (!currency[1].equals(ExchangeRateConstants.EUR_CURRENCY)
						&& !publishedExchangeRatesMap.containsKey(currency[1]))) {
			throw new ExchangeRateException("unsupported currency", HttpStatus.BAD_REQUEST);
		}

		ExchangeRateModel exchangeModel = new ExchangeRateModel();
		try {
			if (currency[1].equals(ExchangeRateConstants.EUR_CURRENCY)) {
				exchangeModel.setBaseCurrency(ExchangeRateConstants.EUR_CURRENCY);
				Rate rate = new Rate();
				Map<String, String> rateMap = new HashMap<String, String>();

				rateMap.put(currency[0], publishedExchangeRatesMap.get(currency[0]));
				rate.setExchangeRate(rateMap);
				exchangeModel.setRate(rate);
				recordViewCountsForCurrency(currency, viewCount);

			} else {

				exchangeModel.setBaseCurrency(currency[1]);
				Rate rate = new Rate();
				Map<String, String> rateMap = new HashMap<String, String>();
				String originalCurrencyRate = null;
				if (currency[0].equals(ExchangeRateConstants.EUR_CURRENCY)) {
					originalCurrencyRate = "1";
				} else {
					originalCurrencyRate = publishedExchangeRatesMap.get(currency[0]);
				}
				String calcluatedRate = ExchangeRateUtil.calculateNonEurRates(originalCurrencyRate,
						publishedExchangeRatesMap.get(currency[1]));

				rateMap.put(currency[0], calcluatedRate);
				rate.setExchangeRate(rateMap);
				exchangeModel.setRate(rate);
			}
		} catch (Exception e) {
			throw new ExchangeRateException("Exception while getting exchange rates", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("retriving exchange rates completed ");
		return exchangeModel;
	}

	/**
	 * record view count for supported currency
	 * 
	 * @param currency
	 * @param viewCount
	 */
	private void recordViewCountsForCurrency(String[] currency, Map<String, Integer> viewCount) {
		log.info("inside recordViewCountsForCurrency() method of ExchangeRateServiceImpl");
		int counter = 1;

		if (viewCount.containsKey(currency[0])) {
			int value = viewCount.get(currency[0]);
			value = value + 1;
			viewCount.put(currency[0], value);
		} else {
			viewCount.put(currency[0], counter);
		}

	}

	@Override
	public SupportedCurrenciesModel getSupportedCurrenciesAndRequestCount(Map<String, String> publishedExchangeRatesMap)
			throws ExchangeRateException {
		log.info("inside getSupportedCurrenciesAndRequestCount() method of ExchangeRateServiceImpl");
		SupportedCurrenciesModel currencyModel = new SupportedCurrenciesModel();
		SupportedCurrency currency = null;
		List<SupportedCurrency> listOfCurrency = new ArrayList<>();
		try {
			for (Entry<String, String> entry : publishedExchangeRatesMap.entrySet()) {
				currency = new SupportedCurrency();
				currency.setCurrency(entry.getKey());
				listOfCurrency.add(currency);

			}
			for (Entry<String, Integer> entry : viewCount.entrySet()) {
				for (SupportedCurrency list : listOfCurrency) {
					if (list.getCurrency().equals(entry.getKey())) {
						list.setNumberOfRequests(entry.getValue());
					}
				}

			}

			List<SupportedCurrency> sortedList = listOfCurrency.stream()
					.sorted(Comparator.comparingInt(SupportedCurrency::getNumberOfRequests).reversed())
					.collect(Collectors.toList());

			currencyModel.setSupportedCurrency(sortedList);
			log.info("retrive supported currency completed");
		} catch (Exception e) {
			throw new ExchangeRateException("Exception in fetching supported currencies",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return currencyModel;
	}

	@Override
	public ConvertorModel currencyConvertor(String amount, String currency, String conversionCurrency,
			Map<String, String> publishedExchangeRatesMap) throws ExchangeRateException {
		log.info("inside currencyConvertor() method of ExchangeRateServiceImpl");
		String requestedCurrency=currency.toUpperCase();
		String convertToCurrency=conversionCurrency.toUpperCase();
		if (requestedCurrency.equals(conversionCurrency)) {
			throw new ExchangeRateException("Invalid Input", HttpStatus.BAD_REQUEST);
		}

		if ((!requestedCurrency.equals(ExchangeRateConstants.EUR_CURRENCY) && !publishedExchangeRatesMap.containsKey(requestedCurrency))
				|| (!convertToCurrency.equals(ExchangeRateConstants.EUR_CURRENCY)
						&& !publishedExchangeRatesMap.containsKey(convertToCurrency))) {
			throw new ExchangeRateException("unsupported currency", HttpStatus.BAD_REQUEST);
		}

		ConvertorModel convertorModel = new ConvertorModel();
		try {

			if (requestedCurrency.equals(ExchangeRateConstants.EUR_CURRENCY)) {
				String converionRate = publishedExchangeRatesMap.get(convertToCurrency);

				getConvertedRate(amount, convertToCurrency, convertorModel, converionRate);
			} else {
				String originalCurrencyRate = null;
				if (convertToCurrency.equals(ExchangeRateConstants.EUR_CURRENCY)) {
					originalCurrencyRate = "1";
				} else {
					originalCurrencyRate = publishedExchangeRatesMap.get(convertToCurrency);
				}
				String calcluatedRate = ExchangeRateUtil.calculateNonEurRates(originalCurrencyRate,
						publishedExchangeRatesMap.get(requestedCurrency));
				getConvertedRate(amount, convertToCurrency, convertorModel, calcluatedRate);
			}
			log.info("currencyConversion completed ");	
		} catch (Exception e) {
			throw new ExchangeRateException("Exception in currency conversion", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return convertorModel;
	}

	/**
	 * calculate the amount from one currency to other currency
	 * 
	 * 
	 * @param amount
	 * @param conversionCurrency
	 * @param convertorModel
	 * @param converionRate
	 */
	private void getConvertedRate(String amount, String conversionCurrency, ConvertorModel convertorModel,
			String converionRate) {
		String result = null;
		Float convertdValue = Float.parseFloat(amount) * Float.valueOf(converionRate);
		result = String.valueOf(convertdValue) + ' ' + conversionCurrency;
		convertorModel.setConvertedRate(result);
	}

}
