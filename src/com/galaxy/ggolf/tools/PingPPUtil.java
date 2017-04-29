package com.galaxy.ggolf.tools;

import java.util.HashMap;
import java.util.Map;

import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.exception.RateLimitException;
import com.pingplusplus.model.Charge;

public class PingPPUtil {
	
	public void test() throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException, ChannelException, RateLimitException{
		Pingpp.apiKey = "sk_test_ibbTe5jLGCi5rzfH4OqPW9KC";
		Pingpp.privateKeyPath = "";
		Map<String, Object> chargeParams = new HashMap<String, Object>();
		chargeParams.put("order_no",  "123456789");
		chargeParams.put("amount", 100);
		Map<String, String> app = new HashMap<String, String>();
		app.put("id", "app_1Gqj58ynP0mHeX1q");
		chargeParams.put("app", app);
		chargeParams.put("channel",  "upacp");
		chargeParams.put("currency", "cny");
		chargeParams.put("client_ip",  "127.0.0.1");
		chargeParams.put("subject",  "Your Subject");
		chargeParams.put("body",  "Your Body");
		Map<String, String> initialMetadata = new HashMap<String, String>();
		initialMetadata.put("color", "red");
		initialMetadata.put("phone_number", "13918651111");
		chargeParams.put("metadata", initialMetadata);

		Charge.create(chargeParams);
		
	}

}
