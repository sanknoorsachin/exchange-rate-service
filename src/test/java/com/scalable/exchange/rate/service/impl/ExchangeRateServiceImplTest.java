package com.scalable.exchange.rate.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.scalable.exchange.rate.exception.ExchangeRateException;
import com.scalable.exchange.rate.model.ConvertorModel;
import com.scalable.exchange.rate.model.ExchangeRateModel;
import com.scalable.exchange.rate.model.SupportedCurrenciesModel;

public class ExchangeRateServiceImplTest {

	private ExchangeRateServiceImpl exchangeRateServiceImpl;
	private Map<String, String> publishedExchangeRatesMap;

	@BeforeEach
	public void setup() {
		publishedExchangeRatesMap = new HashMap<String, String>();
		publishedExchangeRatesMap.put("USD", "1.1345");
		publishedExchangeRatesMap.put("JPY", "129.86");
		publishedExchangeRatesMap.put("BGN", "1.9558");
		publishedExchangeRatesMap.put("CZK", "24.313");
		publishedExchangeRatesMap.put("DKK", "7.4419");
		publishedExchangeRatesMap.put("GBP", "0.83168");
		publishedExchangeRatesMap.put("HUF", "355.88");
		publishedExchangeRatesMap.put("PLN", "4.5229");
		publishedExchangeRatesMap.put("RON", "4.9449");
		publishedExchangeRatesMap.put("SEK", "10.3428");
		publishedExchangeRatesMap.put("CHF", "1.0383");
		publishedExchangeRatesMap.put("ISK", "145.20");
		publishedExchangeRatesMap.put("NOK", "9.9368");
		publishedExchangeRatesMap.put("HRK", "7.5238");

		HashMap<String, Integer> viewCount = new HashMap<String, Integer>();
		exchangeRateServiceImpl = new ExchangeRateServiceImpl(viewCount);
	}

	/**
	 * Test for eur exchange rates
	 * 
	 * @throws ExchangeRateException
	 */
	@Test
	public void test_getExchangeRatesForEUR() throws ExchangeRateException {
		String currencyPair = "USD/EUR";
		ExchangeRateModel model = exchangeRateServiceImpl.getExchangeRates(currencyPair, publishedExchangeRatesMap);
		Map<String, String> exchangeRate = model.getRate().getExchangeRate();
		assertTrue(model instanceof ExchangeRateModel);
		assertEquals(exchangeRate.get("USD"), "1.1345");

	}

	/**
	 * Test for non eur exchange rates
	 * 
	 * @throws ExchangeRateException
	 */
	@Test
	public void test_getExchangeRatesForNonEUR() throws ExchangeRateException {
		String currencyPair = "HUF/USD";
		ExchangeRateModel model = exchangeRateServiceImpl.getExchangeRates(currencyPair, publishedExchangeRatesMap);
		Map<String, String> exchangeRate = model.getRate().getExchangeRate();
		assertTrue(model instanceof ExchangeRateModel);
		assertEquals(exchangeRate.get("HUF"), "313.68884");
	}

	 /**
	  * Test for exception while getting rates
	  * 
	  * @throws ExchangeRateException
	  */
	@Test
	public void test_getExchangeRates_invalid_input() throws ExchangeRateException {
		Exception exception = assertThrows(ExchangeRateException.class, () -> {
			String currencyPair = "EUR/EUR";
			exchangeRateServiceImpl.getExchangeRates(currencyPair, publishedExchangeRatesMap);
		});

		assertEquals("Invalid Input", exception.getMessage());
	}
	
	/**
	 * Test for unsupported currency exception while getting rates
	 * 
	 * @throws ExchangeRateException
	 */
	@Test
	public void test_getExchangeRates_unsupported_currency() throws ExchangeRateException {
		Exception exception = assertThrows(ExchangeRateException.class, () -> {
			String currencyPair = "CFFF/EUR";
			exchangeRateServiceImpl.getExchangeRates(currencyPair, publishedExchangeRatesMap);
		});

		assertEquals("unsupported currency", exception.getMessage());
	}

	/**
	 * Test for get list of supported currency
	 * 
	 * @throws ExchangeRateException
	 */
	@Test
	public void test_getSupportedCurrenciesAndNoOfRequests() throws ExchangeRateException {
		SupportedCurrenciesModel model = exchangeRateServiceImpl
				.getSupportedCurrenciesAndRequestCount(publishedExchangeRatesMap);
		assertTrue(model instanceof SupportedCurrenciesModel);
		assertTrue(model.getSupportedCurrency().size() > 1);
		assertTrue(model.getSupportedCurrency().get(0).getNumberOfRequests() == 0);

	}

	/**
	 * Test for currency convertor
	 * 
	 * @throws ExchangeRateException
	 */
	@Test
	public void test_currencyConvertor() throws ExchangeRateException {
		ConvertorModel model = exchangeRateServiceImpl.currencyConvertor("15", "EUR", "GBP",
				publishedExchangeRatesMap);
		assertTrue(model instanceof ConvertorModel);
		assertEquals(model.getConvertedRate(), "12.4752 GBP");
	}
}
