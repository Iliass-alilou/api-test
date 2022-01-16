package com.Micro.Services.reviewservice.exceptions;

public class InvalidTokenException extends Exception {

	public InvalidTokenException() {
	}
	
	public InvalidTokenException(String string) {
		super(string);
	}

	private static final long serialVersionUID = 1L;

}
