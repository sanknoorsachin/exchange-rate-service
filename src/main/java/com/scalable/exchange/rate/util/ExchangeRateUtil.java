package com.scalable.exchange.rate.util;

public class ExchangeRateUtil {

	
	/**
	 * Calculate Non EURO rates
	 * 
	 * @param originalCurrencyRate
	 * @param baseCurrencyRate
	 * @return
	 */
	public static String calculateNonEurRates(String originalCurrencyRate, String baseCurrencyRate) {
		float calculatedRate = Float.parseFloat(originalCurrencyRate) / Float.parseFloat(baseCurrencyRate);
		return Float.toString(calculatedRate);

	}
}
