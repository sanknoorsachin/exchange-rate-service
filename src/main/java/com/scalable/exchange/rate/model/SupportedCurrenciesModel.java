package com.scalable.exchange.rate.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupportedCurrenciesModel {
	
	private List<SupportedCurrency> supportedCurrency;
	

}
