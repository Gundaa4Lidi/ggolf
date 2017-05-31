package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.Article;

public class ArticleData {
	private int count;
	private Collection<Article> articles;
	public ArticleData(int count, Collection<Article> articles) {
		this.count = count;
		this.articles = articles;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Collection<Article> getArticles() {
		return articles;
	}
	public void setArticles(Collection<Article> articles) {
		this.articles = articles;
	}
	
	
	
	
}
