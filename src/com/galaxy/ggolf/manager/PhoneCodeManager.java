package com.galaxy.ggolf.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.cache.GenericCache;
import com.galaxy.ggolf.dao.PhoneCodeDAO;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.PhoneCode;



public class PhoneCodeManager {

	public GenericCache<String, PhoneCode> phoneCodeCache;
	public PhoneCodeDAO phoneCodeDAO;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public PhoneCodeManager(PhoneCodeDAO phoneCodeDAO) {
		this.phoneCodeCache = new GenericCache<String, PhoneCode>();
		this.phoneCodeDAO = phoneCodeDAO;
	}

	public void createPhoneCode(String phone, String code)
			throws GalaxyLabException {
		PhoneCode phonecode = new PhoneCode(phone, code);
		if (!phoneCodeDAO.createPhoneCode(phone, code)) {
			throw new GalaxyLabException("Error in create user");
		}
/*		PhoneCode phonecode1 = phoneCodeDAO.getCodeByPhone(phone);
		phoneCodeCache.put(phonecode1.getPhone(), phonecode1);*/
	}

	public PhoneCode getcodeByPhone(String phone) {

		PhoneCode phonecode = phoneCodeDAO.getCodeByPhone(phone);
/*			if (phonecode != null) {
				this.phoneCodeCache.put(phonecode.getPhone(), phonecode);
			}*/
		return phonecode;
	}


}
