package com.galaxy.ggolf.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloopen.rest.sdk.utils.encoder.BASE64Decoder;
import com.galaxy.ggolf.domain.Common_config;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.Staff;
import com.galaxy.ggolf.jdbc.CommonConfig;
import com.galaxy.ggolf.manager.Common_configManager;
import com.galaxy.ggolf.tools.FileUtil;


//@Consumes("multipart/form-data")
@Produces("application/json")
@Path("/Config")
public class ConfigService extends BaseService {

	private final Common_configManager manager;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ConfigService(Common_configManager manager) {
		this.manager = manager;
	}
	
	@POST
	@Path("/")
	public String saveConfig(String data) {
		Common_config config = super.fromGson(data, Common_config.class);
		try {
			logger.debug("value---------",config.getDescribe());
			String head = config.getVALUE();
			if(head.indexOf("data:image/png;base64")!=-1){
				FileUtil.createDirIfNotExist(CommonConfig.DEFAULT_UPLOAD_PATH);
				String base64Img = head.split(",")[1];
				BufferedImage image = null;
				byte[] imgByte;
				BASE64Decoder decoder = new BASE64Decoder();
				imgByte = decoder.decodeBuffer(base64Img);
				ByteArrayInputStream bis = new ByteArrayInputStream(imgByte);
				image = ImageIO.read(bis);
				bis.close();
				String date = DateTime.now().getMillis()+"";
				String picnameString = "defaultHead.png";
				File outputfile = new File(CommonConfig.DEFAULT_UPLOAD_PATH+picnameString);
				FileUtil.deleteFile(outputfile);
				ImageIO.write(image, "png", outputfile);
				String url = CommonConfig.CONNECT + CommonConfig.FILE_DOWNLOAD + "default_" + picnameString+ "?t=" + date;
				
				http://192.168.1.107:8085/GGolfz/rest/file/download/user_UH_UH@22.png?t=20170405_151523
				config.setVALUE(url);
			}
			this.manager.saveConfig(config);
			return getSuccessResponse();
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	@GET
	@Path("/getConfig/{key}")
	public String getConfig(@PathParam("key") String key){
		try {
			return getResponse(this.manager.getCommon_cofigByKey(key));
		} catch (Exception ex) {
			logger.error("Error occured", ex);
			return getErrorResponse();
		}
	}
	
	@POST
	@Path("/updateConfig")
	@Produces("text/plain")
	public String updateConfig(@FormParam("key") String key, @FormParam("value") String value){
		try {
			if(manager.updateConfig(key, value)){
				return getSuccessResponse();
			}
		} catch (Exception ex) {
			logger.error("Error occured", ex);
		}
		return getErrorResponse();
	}
	
	@GET
	@Path("/getAll")
	public String getAll(){
		try {
			Collection<Common_config> config = manager.getall();
			return getResponse(config);
		} catch (Exception e) {
			logger.error("Error occured", e);
		}
		return getErrorResponse();
	}


}
