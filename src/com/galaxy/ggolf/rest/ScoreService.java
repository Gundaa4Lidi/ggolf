package com.galaxy.ggolf.rest;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import com.galaxy.ggolf.domain.Score;
import com.galaxy.ggolf.manager.ScoreManager;

@Produces("application/json")
@Consumes("application/json")
@Path("/Score")
public class ScoreService extends BaseService{
     
	 private ScoreManager scoreManager;

	public ScoreService(ScoreManager scoreManager) {
		super();
		this.scoreManager = scoreManager;
	}
	 
	 @POST
	 @Path("/create")
	 public String CreateScore(String data,@Context HttpHeaders headers){
		try {
			Score sc=super.fromGson(data, Score.class);		   			 		    
			 String ScoreOne=sc.getScoresOne();			
			 String status="1";
			//设置记分是否完成状态码，0：未完成，1：完成
			String[] Scores=ScoreOne.split("&");
			for(int i=0;i<Scores.length;i++){
				 String[] str=Scores[i].split(",");
				for(int j=0;j<str.length;j++){
					if(str[j].equals("0")){
						status="0";
						break;
					}
				}
			}
			
			sc.setStatus(status);			
		       
			 if(scoreManager.createScore(sc)){
				 return getSuccessResponse();
			 }
		 } catch (Exception e) {
			  logger.error("Error ccured",e);
		} 
		         return getErrorResponse();
	 }
	 
	 @GET
	 @Path("/getScores")
	 public String getScore(@FormParam("UserId")String UserId,@Context HttpHeaders headers){
		try{
		 if(UserId!=null && !UserId.equals("")){
			Collection<Score> scores= scoreManager.getScore(UserId);
			   
		       return getResponse(scores); 
		 }
		}catch(Exception e){
			logger.error("Error cured",e);
		}
		       return getErrorResponse();
	 }
	 
	 @GET
	 @Path("/delectScore")
	 public String delectScore(@FormParam("UserId")String UserId,
			 @FormParam("ScoreId")String ScoreId,@Context HttpHeaders headers){
		 try{
          if(scoreManager.deleteScore(UserId, ScoreId)){
        	  return getSuccessResponse();
          }
		 }catch(Exception e){
			logger.error("Error occured",e); 
		 }
		      return getErrorResponse();
	 }
}
