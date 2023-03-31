package com.scalable.exchange.rate.service;

import java.util.Map;

import com.scalable.exchange.rate.exception.ExchangeRateException;
import com.scalable.exchange.rate.model.ConvertorModel;
import com.scalable.exchange.rate.model.ExchangeRateModel;
import com.scalable.exchange.rate.model.SupportedCurrenciesModel;

public interface ExchangeRateService {

	/** get exchange rates for EUR and Non Eur currency pair
	 * 
	 * @param currencyPair
	 * @param publishedExchangeRatesMap
	 * @return
	 * @throws ExchangeRateException
	 */
	public ExchangeRateModel getExchangeRates(String currencyPair,Map<String, String> publishedExchangeRatesMap) throws ExchangeRateException;

	/**
	 * get list of supported currencies along with the request count
	 * 
	 * @param publishedExchangeRatesMap
	 * @return
	 * @throws ExchangeRateException
	 */
	public SupportedCurrenciesModel getSupportedCurrenciesAndRequestCount(Map<String, String> publishedExchangeRatesMap) throws ExchangeRateException;

	/**
	 * Convert given amount from one currency to another currency
	 * 
	 * @param amount
	 * @param currency
	 * @param conversionCurrency
	 * @param publishedExchangeRatesMap
	 * @return
	 * @throws ExchangeRateException
	 */
	public ConvertorModel currencyConvertor(String amount, String currency, String conversionCurrency,Map<String, String> publishedExchangeRatesMap) throws ExchangeRateException;

}
