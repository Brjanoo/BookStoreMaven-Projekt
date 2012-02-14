package com.pl.exception;

public class PriceLessThanZeroException extends Exception {

	//private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PriceLessThanZeroException(String message) {
		super(message);
	}

}