package com.galaxy.ggolf.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.manager.CommentManager;

//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/Comment")
public class CommentService extends BaseService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private CommentManager manager;
	
	public CommentService(CommentManager manager) {
		this.manager = manager;
	}
	
	
}
