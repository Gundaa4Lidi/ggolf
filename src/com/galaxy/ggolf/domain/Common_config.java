package com.galaxy.ggolf.domain;

public class Common_config {

	private String KEY;

	private String Describe;
	
	private String VALUE;
	
	
	
	

	public String getDescribe() {
		return Describe;
	}



	public void setDescribe(String describe) {
		Describe = describe;
	}



	public String getVALUE() {
		return VALUE;
	}



	public void setVALUE(String vALUE) {
		VALUE = vALUE;
	}



	public String getKEY() {
		return KEY;
	}



	public void setKEY(String kEY) {
		KEY = kEY;
	}




	public Common_config(String KEY,String Describe, String VALUE) {
		super();
		this.KEY = KEY;
		this.Describe = Describe;
		this.VALUE = VALUE;

	}
}
