package com.galaxy.ggolf.domain;

public class GalaxyLabException extends java.lang.Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	public String getMessage() {
		return message;
	}

	public GalaxyLabException(String message) {
		super();
		this.message = message;
	}

}
