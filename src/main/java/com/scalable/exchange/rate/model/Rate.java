package com.scalable.exchange.rate.model;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rate {
  
	private Map<String,String> exchangeRate;
}
