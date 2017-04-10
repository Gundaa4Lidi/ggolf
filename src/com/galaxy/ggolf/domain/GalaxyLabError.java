package com.galaxy.ggolf.domain;

public class GalaxyLabError {

	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public GalaxyLabError(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
	}

}
