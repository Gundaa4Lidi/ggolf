package com.galaxy.ggolf.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.cache.GenericCache;
import com.galaxy.ggolf.dao.Common_configDAO;
import com.galaxy.ggolf.domain.Common_config;
import com.galaxy.ggolf.domain.GalaxyLabException;


public class Common_configManager {

	public GenericCache<String, Common_config> common_configCache;
	public Common_configDAO common_configDAO;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public Common_configManager(Common_configDAO common_configDAO) {
		this.common_configCache = new GenericCache<String, Common_config>();
		this.common_configDAO = common_configDAO;
	}

	public void createFee(Common_config config)
			throws GalaxyLabException {
		if (!common_configDAO.createFee(config)) {
			throw new GalaxyLabException("Error in create common_config");
		}
		Common_config common_config1 = common_configDAO.getCommon_cofig(config.getKEY());
		common_configCache.put(common_config1.getKEY(), common_config1);
	}

	public Common_config getCommon_cofigByKey(String key) {
		if (common_configCache.get(key) != null) {
			logger.debug("cache hit");
			return common_configCache.get(key);
		} else {
			logger.debug("cache missed, looking up from DB");
			Common_config common_config = common_configDAO.getCommon_cofig(key);
			if (common_config != null) {
				this.common_configCache.put(common_config.getKEY(), common_config);
			}
			return common_config;
		}
	}
	
	public boolean updateConfig(String key, String value) throws Exception{
		boolean updated = this.common_configDAO.updateFee(key, value);
		if(updated){
			Common_config currentConfig = this.common_configCache.get(key);
			currentConfig.setVALUE(value);
			this.common_configCache.put(key, currentConfig);
		}
		return updated;
	}
	
	public Collection<Common_config> getall() throws Exception{
		Collection<Common_config> configs = common_configDAO.getAllFee();
		return configs;
		
	}

	public void saveConfig(Common_config config) throws Exception {
		Common_config config1 = this.common_configDAO.getCommon_cofig(config.getKEY());
		if(config1 != null){
			updateConfig(config.getKEY(), config.getVALUE());
		}else{
			createFee(config);
		}
	}
	


}
