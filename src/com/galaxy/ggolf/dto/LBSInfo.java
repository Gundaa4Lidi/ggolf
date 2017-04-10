package com.galaxy.ggolf.dto;

public class LBSInfo {
	private String status;
	
	private String info;

	private String infocode;

	private regeocode regeocode;


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getInfocode() {
		return infocode;
	}

	public void setInfocode(String infocode) {
		this.infocode = infocode;
	}

	public regeocode getRegeocode() {
		return regeocode;
	}

	public void setRegeocode(regeocode regeocode) {
		this.regeocode = regeocode;
	}

	
public class regeocode{
	private String formatted_address;

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}
	public class addressComponent{
		
	}
}
}