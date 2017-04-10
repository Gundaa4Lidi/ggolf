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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.CarouselDAO;
import com.galaxy.ggolf.domain.Carousel;

@Consumes("application/json")
@Produces("application/json")
@Path("/MainView")
public class MainViewService extends BaseService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private CarouselDAO carouselDAO;

	public MainViewService(CarouselDAO carouselDAO) {
		this.carouselDAO = carouselDAO;
	}
	
	/**
	 * 保存轮播图
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveCarousel")
	public String save(String data,@Context HttpHeaders headers){
		try {
			Carousel cos = fromGson(data, Carousel.class);
			if(cos.getUID()!=null&&this.carouselDAO.getByUID(cos.getUID())!=null){
				this.carouselDAO.update(cos);
			}else{
				this.carouselDAO.create(cos);
			}
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured", e);
			return getErrorResponse();
		}
		
	}
	
	/**
	 * 获取所有轮播信息
	 * @return
	 */
	@GET
	@Path("/getCarousel")
	public String getCarousel(){
		try {
			Collection<Carousel> result = this.carouselDAO.getAll(); 
			return getResponse(result);
		} catch (Exception e) {
			logger.error("Error occured", e);
			return getErrorResponse();
		}
	}
	
	/**
	 * 删除轮播信息
	 * @param UID
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/removeCarousel")
	public String removeCarousel(@FormParam("UID") String UID,
			@Context HttpHeaders headers){
		try {
			if(this.carouselDAO.delete(UID)){
				return getSuccessResponse();
			}
		} catch (Exception e) {
			logger.error("Error occured", e);
			return getErrorResponse();
		}
		return getErrorResponse();
	}
	
	
}
