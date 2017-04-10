package com.galaxy.ggolf.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.CollectDAO;
import com.galaxy.ggolf.dao.LikeDAO;
import com.galaxy.ggolf.domain.Collect;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.Likes;

public class CollectAndLikeManager {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private LikeDAO likeDAO;
	
	private CollectDAO collectDAO;
	
	public CollectAndLikeManager(LikeDAO likeDAO, CollectDAO collectDAO) {
		this.likeDAO = likeDAO;
		this.collectDAO = collectDAO;
	}
	
	public void createLike(Likes like)throws Exception{
		if(!this.likeDAO.create(like)){
			throw new GalaxyLabException("Error in create like");
			
		}
	}
	
	public void createCollect(Collect col)throws Exception{
		if(!this.collectDAO.create(col)){
			throw new GalaxyLabException("Error in create collect");
		}
	}
	
	
	
}
