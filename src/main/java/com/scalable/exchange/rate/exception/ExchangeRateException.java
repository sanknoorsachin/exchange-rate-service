package com.scalable.exchange.rate.exception;

import org.springframework.http.HttpStatus;

public class ExchangeRateException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;
	private HttpStatus status;

	public ExchangeRateException(String message, HttpStatus status) {
		super();
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
