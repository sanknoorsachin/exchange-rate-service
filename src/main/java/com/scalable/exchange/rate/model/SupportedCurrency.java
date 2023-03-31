package com.scalable.exchange.rate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupportedCurrency {

	private String currency;
	private Integer numberOfRequests=0;
	
}
