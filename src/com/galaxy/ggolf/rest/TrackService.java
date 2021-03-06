package com.galaxy.ggolf.rest;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import com.galaxy.ggolf.domain.Track;
import com.galaxy.ggolf.manager.TrackManager;

@Produces("application/json")
@Path("/Track")
public class TrackService extends BaseService {

    private TrackManager trackManager;

    public TrackService(TrackManager trackManager) {
	super();
	this.trackManager = trackManager;
    }
    //创建足迹
    @POST
    @Path("/create")
    public String CreateFootPrint(String data, @Context HttpHeaders headers) {
	try {
	    Track tk = fromGson(data, Track.class);
	    if (trackManager.createTrack(tk)) {
		return getSuccessResponse();
	    }
	} catch (Exception e) {
	    logger.error("Error occured", e);
	}
	return getErrorResponse();

    }
    //获取足迹
    @GET
    @Path("/getTrackList")
    public String getTrackList(@FormParam("UserId") String UserId, @Context HttpHeaders headers) {
	try {
	    if (UserId != null && !UserId.equals("")) {
		Collection<Track> tracks = trackManager.getTrackList(UserId);
		return getResponse(tracks);
	    }
	} catch (Exception e) {
	    logger.error("Error occured", e);
	}
	return getErrorResponse();
    }
    
    
    //删除足迹
    @GET
    @Path("/deleteTrack")
    public String deleteTrack(@FormParam("UserId") String UserId, @FormParam("TrackId") String TrackId,
	    @Context HttpHeaders headers) {
	try {
	    if (UserId != null && !UserId.equals("") && TrackId != null && !TrackId.equals("")) {
		trackManager.deleteTrack(UserId, TrackId);
		return getSuccessResponse();
	    }
	} catch (Exception e) {
	    logger.error("Error occured", e);
	}
	return getErrorResponse();
    }
    
    // 获取足迹详情
    @GET
    @Path("/getTrackDetail")
    public String getTrackDetail(@FormParam("UserId") String UserId, @FormParam("TrackId") String TrackId,
	    @Context HttpHeaders headers) {

	try {
	    if (UserId != null && !UserId.equals("") && TrackId != null && !TrackId.equals("")) {
		Collection<Track> gtd = trackManager.getTrackDetail(UserId, TrackId);
		return getResponse(gtd);
	    }
	} catch (Exception e) {
	    logger.error("Error occured", e);
	}
	return getErrorResponse();

    }
}
