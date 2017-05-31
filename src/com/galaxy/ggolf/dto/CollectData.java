package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.Article;
import com.galaxy.ggolf.domain.ArticleSubject;
import com.galaxy.ggolf.domain.Club;
import com.galaxy.ggolf.domain.User;

public class CollectData {
	private int count;
	private Collection<User> userlist;
	private Collection<Article> articles;
	private Collection<ArticleSubject> subjects;
	private Collection<Club> clubs;
	
	public CollectData() {
		
	}
	public CollectData(int count, Collection<User> userlist, Collection<Article> articles,
			Collection<ArticleSubject> subjects, Collection<Club> clubs) {
		this.count = count;
		this.userlist = userlist;
		this.articles = articles;
		this.subjects = subjects;
		this.clubs = clubs;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Collection<User> getUserlist() {
		return userlist;
	}

	public void setUserlist(Collection<User> userlist) {
		this.userlist = userlist;
	}

	public Collection<Article> getArticles() {
		return articles;
	}

	public void setArticles(Collection<Article> articles) {
		this.articles = articles;
	}

	public Collection<ArticleSubject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Collection<ArticleSubject> subjects) {
		this.subjects = subjects;
	}

	public Collection<Club> getClubs() {
		return clubs;
	}

	public void setClubs(Collection<Club> clubs) {
		this.clubs = clubs;
	}

	
	
	
	
	
}
