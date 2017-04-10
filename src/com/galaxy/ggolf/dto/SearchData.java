package com.galaxy.ggolf.dto;

import java.util.Collection;

import com.galaxy.ggolf.domain.Article;
import com.galaxy.ggolf.domain.Club;
import com.galaxy.ggolf.domain.Coach;
import com.galaxy.ggolf.domain.Message;
import com.galaxy.ggolf.domain.User;

public class SearchData {
	private Collection<Club> clubs;
	private Collection<User> users;
	private Collection<Message> messages;
	private Collection<Article> articles;
	private Collection<Coach> coachs;
	
	public SearchData() {
		
	}
	
	public SearchData(Collection<Club> clubs, Collection<User> users, Collection<Message> messages,
			Collection<Article> articles, Collection<Coach> coachs) {
		this.clubs = clubs;
		this.users = users;
		this.messages = messages;
		this.articles = articles;
		this.coachs = coachs;
	}
	public Collection<Club> getClubs() {
		return clubs;
	}
	public void setClubs(Collection<Club> clubs) {
		this.clubs = clubs;
	}
	public Collection<User> getUsers() {
		return users;
	}
	public void setUsers(Collection<User> users) {
		this.users = users;
	}
	public Collection<Message> getMessages() {
		return messages;
	}
	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}
	public Collection<Article> getArticles() {
		return articles;
	}
	public void setArticles(Collection<Article> articles) {
		this.articles = articles;
	}
	public Collection<Coach> getCoachs() {
		return coachs;
	}
	public void setCoachs(Collection<Coach> coachs) {
		this.coachs = coachs;
	}
	
	
}
