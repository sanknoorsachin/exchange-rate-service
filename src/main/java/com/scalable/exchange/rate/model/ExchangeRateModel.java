package com.scalable.exchange.rate.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateModel {
	
	private String baseCurrency;
	private Rate rate;
	
	

}
