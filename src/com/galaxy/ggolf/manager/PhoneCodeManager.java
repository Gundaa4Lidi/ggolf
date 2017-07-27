package com.galaxy.ggolf.manager;

import com.galaxy.ggolf.jdbc.CommonConfig;
import com.galaxy.ggolf.tools.CipherUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.cache.GenericCache;
import com.galaxy.ggolf.dao.PhoneCodeDAO;
import com.galaxy.ggolf.domain.GalaxyLabException;
import com.galaxy.ggolf.domain.PhoneCode;

import java.text.SimpleDateFormat;
import java.util.Date;


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

	public String ValidCode(String phone,String code){
		String result = "";
		long min = CommonConfig.MIN;
		try {
			PhoneCode pcode = getcodeByPhone(phone);
			if (pcode.getCode().equals(code)) {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date lasttime = format.parse(pcode.getDatetime());
				Date now = new Date();
				long diff = now.getTime() - lasttime.getTime();

				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				logger.info("" + diffDays + diffHours + diffMinutes
						+ diffSeconds);
				if (diffDays > 0 || diffHours > 0 || diffMinutes >= min) {
					result = "验证码已超时，请重新获取！";
				}
			} else {
				result = "验证码错误,或与判定的手机不匹配";
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}


}
