package com.galaxy.ggolf.dto;

public class FileList {

	private String url;
	private String thumb;
	private String name;
	private String src;
	
	public FileList() {
	}
	public FileList(String url, String thumb, String name) {
		this.url = url;
		this.thumb = thumb;
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	
	
}
