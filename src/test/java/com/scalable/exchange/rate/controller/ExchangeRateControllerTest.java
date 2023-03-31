package com.scalable.exchange.rate.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.scalable.exchange.rate.exception.ExchangeRateException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExchangeRateControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	
	/**Test for success senario for getting eur rate for pair - INR/EUR
	 * 
	 * @throws ExchangeRateException
	 */
	@Test
	public void getExchangeRatesForEur_success() throws ExchangeRateException {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/exchangerate?currencyPair=INR/EUR",
				String.class)).contains("{\"baseCurrency\":\"EUR\",\"rate\":{\"exchangeRate\":{\"INR\":\"84.4135\"}}}");
	}
	
	/**
	 * Test for invalid input while getting eur rates 
	 * 
	 * @throws ExchangeRateException
	 */
	@Test
	public void getExchangeRatesForEur_InvalidInput_Exception() throws ExchangeRateException {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/exchangerate?currencyPair=EUR/EUR",
				String.class)).overridingErrorMessage("Invalid Input", ExchangeRateException.class);
	}
	
	/**
	 * Test for unsupported currency while getting rates
	 * 
	 * @throws ExchangeRateException
	 */
	@Test
	public void getExchangeRatesForEur_unsupportedcurrency_Exception() throws ExchangeRateException {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/exchangerate?currencyPair=AAA/EUR",
				String.class)).overridingErrorMessage("unsupported currency", ExchangeRateException.class);
	}

	/**
	 * Test for fetching Non EUR rates for currency pair- HUF/USD
	 * 
	 * @throws ExchangeRateException
	 */
	@Test
	public void getExchangeRatesForNonEur_success() throws ExchangeRateException {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/exchangerate?currencyPair=HUF/USD",
				String.class))
						.contains("{\"baseCurrency\":\"USD\",\"rate\":{\"exchangeRate\":{\"HUF\":\"313.68884\"}}}");
	}
	
	
	/**
	 * Test for invalid input while getting non eur rates 
	 * 
	 * @throws ExchangeRateException
	 */
	
	@Test
	public void getExchangeRatesForNonEur_InvalidInput_Exception() throws ExchangeRateException {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/exchangerate?currencyPair=USD/USD",
				String.class))
		.overridingErrorMessage("Invalid Input", ExchangeRateException.class);
	}
	
	/**
	 * Test for unsupported currency while getting non eur rates
	 * 
	 * @throws ExchangeRateException
	 */
	@Test
	public void getExchangeRatesForNonEur_unsupportedcurrency_Exception() throws ExchangeRateException {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/exchangerate?currencyPair=USD/tfaggg",
				String.class)).
		overridingErrorMessage("unsupported currency", ExchangeRateException.class);
	}

	/**
	 * Test to get list of supported currency along with request count
	 * 
	 * @throws ExchangeRateException
	 */
	@Test
	public void getListOfSupportedCurrenciesAndRequestCount_suceess() throws ExchangeRateException {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/supportedcurrencies",
				String.class)).contains(
						"{\"supportedCurrency\":[{\"currency\":\"INR\",\"numberOfRequests\":1},{\"currency\":\"USD\",\"numberOfRequests\":0},{\"currency\":\"JPY\",\"numberOfRequests\":0},{\"currency\":\"BGN\",\"numberOfRequests\":0},{\"currency\":\"CZK\",\"numberOfRequests\":0},{\"currency\":\"DKK\",\"numberOfRequests\":0},{\"currency\":\"GBP\",\"numberOfRequests\":0},{\"currency\":\"HUF\",\"numberOfRequests\":0},{\"currency\":\"PLN\",\"numberOfRequests\":0},{\"currency\":\"RON\",\"numberOfRequests\":0},{\"currency\":\"SEK\",\"numberOfRequests\":0},{\"currency\":\"CHF\",\"numberOfRequests\":0},{\"currency\":\"ISK\",\"numberOfRequests\":0},{\"currency\":\"NOK\",\"numberOfRequests\":0},{\"currency\":\"HRK\",\"numberOfRequests\":0},{\"currency\":\"RUB\",\"numberOfRequests\":0},{\"currency\":\"TRY\",\"numberOfRequests\":0},{\"currency\":\"AUD\",\"numberOfRequests\":0},{\"currency\":\"BRL\",\"numberOfRequests\":0},{\"currency\":\"CAD\",\"numberOfRequests\":0},{\"currency\":\"CNY\",\"numberOfRequests\":0},{\"currency\":\"HKD\",\"numberOfRequests\":0},{\"currency\":\"IDR\",\"numberOfRequests\":0},{\"currency\":\"ILS\",\"numberOfRequests\":0},{\"currency\":\"KRW\",\"numberOfRequests\":0},{\"currency\":\"MXN\",\"numberOfRequests\":0},{\"currency\":\"MYR\",\"numberOfRequests\":0},{\"currency\":\"NZD\",\"numberOfRequests\":0},{\"currency\":\"PHP\",\"numberOfRequests\":0},{\"currency\":\"SGD\",\"numberOfRequests\":0},{\"currency\":\"THB\",\"numberOfRequests\":0},{\"currency\":\"ZAR\",\"numberOfRequests\":0}]}");
	}
	
	/**
	 * Test to convert given amount to given currency
	 * 
	 * @throws ExchangeRateException
	 */
	
	@Test
	public void currencyConvertor_success() throws ExchangeRateException {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/currencyconvertor?amount=15&currency=INR&conversionCurrency=EUR",
				String.class))
						.contains("{\"convertedRate\":\"0.17769669 EUR\"}");
	}
	
	/**
	 * Test for invalid input  for currency convertor
	 * 
	 * @throws ExchangeRateException
	 */
	@Test
	public void currencyConvertor_InvalidInput_Exception() throws ExchangeRateException {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/currencyconvertor?amount=15&currency=EUR&conversionCurrency=EUR",
				String.class))
		.overridingErrorMessage("Invalid Input", ExchangeRateException.class);
	}

	/**
	 * Test for exception case for  currency convertor
	 * 
	 * @throws ExchangeRateException
	 */
	@Test
	public void currencyConvertor_unsupportedcurrency_Exception() throws ExchangeRateException {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/currencyconvertor?amount=15&currency=CCC&conversionCurrency=EUR",
				String.class)).
		overridingErrorMessage("unsupported currency", ExchangeRateException.class);
	}

}
