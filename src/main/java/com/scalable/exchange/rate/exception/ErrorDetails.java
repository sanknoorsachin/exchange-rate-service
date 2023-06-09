package com.scalable.exchange.rate.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ErrorDetails {
	
	private Date timestamp;
	private String message;
	private String details;
	private HttpStatus status;
	
	
	public ErrorDetails(Date timestamp, String message, String details, HttpStatus status) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
		this.status = status;
	}


	public ErrorDetails(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	
	}

	
	
	public Date getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}


	public HttpStatus getStatus() {
		return status;
	}


	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	
	
	

}
