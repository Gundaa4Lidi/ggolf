package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.Article;

public class ArticleData {
	private int Count;
	private Collection<Article> articles;
	public ArticleData(int count, Collection<Article> articles) {
		Count = count;
		this.articles = articles;
	}
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	public Collection<Article> getArticles() {
		return articles;
	}
	public void setArticles(Collection<Article> articles) {
		this.articles = articles;
	}
	
	
}
