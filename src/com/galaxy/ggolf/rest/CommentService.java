package com.galaxy.ggolf.rest;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

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
	
	/**
	 * 获取全部评论
	 * @param days
	 * @param keyword
	 * @param rows
	 * @param headers
	 * @return
	 */
	@GET
	@Path("/getAll")
	public String getAll(@FormParam("days") String days,
			@FormParam("keyword") String keyword,
			@FormParam("rows") String rows,
			@Context HttpHeaders headers){
		try {
			return getResponse(this.manager.getAll(keyword, rows, Integer.parseInt(days)));
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
}
