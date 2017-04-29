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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.dao.HotCityDAO;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.HotCity;

//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/HotCity")
public class HotCityService extends BaseService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private HotCityDAO hotCityDAO;
	
	public HotCityService(HotCityDAO hotCityDAO) {
		this.hotCityDAO = hotCityDAO;
	}
	
	/**
	 * 导入全部城市
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/importCity")
	public String importCity(String data,@Context HttpHeaders headers){
		try {
			Collection<HotCity> hotCities = super.fromJsonArray(data, HotCity.class);
			if(hotCities.size()>0){
				if(!this.hotCityDAO.createAll(hotCities)){
					throw new GalaxyLabException("Error in create HotCity");
				}
				return getSuccessResponse();
			}
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 修改热门城市
	 * @param data
	 * @param headers
	 * @return
	 */
	@POST
	@Path("/saveCity")
	public String saveCity(String data,@Context HttpHeaders headers){
		try {
			HotCity city = super.fromGson(data, HotCity.class);
			if(!this.hotCityDAO.update(city.getUID(), city.getIsHot())){
				throw new GalaxyLabException("Error in update hotCity");
			}
			return getSuccessResponse();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取热门城市
	 * @return
	 */
	@GET
	@Path("/getIsHot")
	public String getIsHot(){
		try {
			Collection<HotCity> hotCities = this.hotCityDAO.getIsHot();
			return getResponse(hotCities);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 根据省份获取城市
	 * @param Province
	 * @return
	 */
	@GET
	@Path("/getCityByProvince")
	public String getCityByProvince(@FormParam("Province") String Province){
		try {
			Collection<HotCity> hotCities = this.hotCityDAO.getCityByProvince(Province);
			return getResponse(hotCities);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
	/**
	 * 获取省份
	 * @return
	 */
	@GET
	@Path("/getProvince")
	public String getProvince(){
		try {
			Collection<HotCity> hcs = new ArrayList<HotCity>();
			Collection<HotCity> hotCities = this.hotCityDAO.getProvince();
			for(HotCity hc : hotCities){
				HotCity hc1 = new HotCity(hc.getProvince());
				hcs.add(hc1);
			}
			return getResponse(hcs);
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
		return getErrorResponse();
	}
	
}
