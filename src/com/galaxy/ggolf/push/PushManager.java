package com.galaxy.ggolf.push;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.dto.PushOption;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.jdbc.CommonConfig;
import org.springframework.util.StringUtils;

public class PushManager {

	private static final Logger logger = LoggerFactory.getLogger(PushManager.class);
	
	private static final String production_mode = "false";
	
	public static final String GoApp = "go_app";

	public static final String GoUrl = "go_url";

	public static final String GoActivity = "go_activity";

	public static final String GoCustom = "go_custom";
	
	public static final String Display_Type_Notify = "notification";
	
	public static final String Display_Type_Msg = "message";
	

	/**
	 * 确认球位的推送消息
	 * @param token
	 * @throws Exception
	 */
	public static void sendConfirm_ball(String token,String OrderID)throws Exception{
		PushUtil pushAD = new PushUtil(CommonConfig.Umeng_AD_AppKey,CommonConfig.Umeng_AD_AppMaster_Secret);
		PushUtil pushIOS = new PushUtil(CommonConfig.Umeng_IOS_AppKey,CommonConfig.Umeng_IOS_AppMaster_Secret);
		int long_token = token.length();
		String ticker = "确认球位";
		String title = "确认球位";
		String text = "已确认球位,点击进入支付界面";
		String after_open = "go_app";
		String display_type = "notification";
		Map<String,String> eMap = new HashMap<String,String>();
		eMap.put("type", "orderStateChange");
		eMap.put("OrderID", OrderID);
		logger.info("发送中");
		if(long_token == CommonConfig.Umeng_Android_Byte){
			pushAD.sendAndroidUnicast(token, ticker, title, text, after_open, display_type, production_mode, null, eMap);
			logger.info("发送成功");
		}else if(long_token == CommonConfig.Umeng_IOS_Byte){
			pushIOS.sendIOSUnicast(token, text, production_mode, null, eMap);
			logger.info("发送成功");
		}
	}
	
	/**
	 * 完成球场订单的推送消息
	 * @param token
	 * @throws Exception
	 */
	public static void sendFinish_booking(String token)throws Exception{
		PushUtil pushAD = new PushUtil(CommonConfig.Umeng_AD_AppKey,CommonConfig.Umeng_AD_AppMaster_Secret);
		PushUtil pushIOS = new PushUtil(CommonConfig.Umeng_IOS_AppKey,CommonConfig.Umeng_IOS_AppMaster_Secret);
		int long_token = token.length();
		String ticker = "完成订单";
		String title = "完成订单";
		String text = "订单已生成,点击查看订单详情.";
		String after_open = "go_app";
		String display_type = "notification";
		if(long_token == CommonConfig.Umeng_Android_Byte){
			pushAD.sendAndroidUnicast(token, ticker, title, text, after_open, display_type, production_mode, null, null);
		}else if(long_token == CommonConfig.Umeng_IOS_Byte){
			pushIOS.sendIOSUnicast(token, text, production_mode, null, null);
		}
	}
	
	/**
	 * 发送教练审核结果
	 * @param token
	 * @param flag
	 * @throws Exception
	 */
	public static void sendVerifyCoach(String token,boolean flag)throws Exception{
		PushUtil pushAD = new PushUtil(CommonConfig.Umeng_AD_AppKey,CommonConfig.Umeng_AD_AppMaster_Secret);
		PushUtil pushIOS = new PushUtil(CommonConfig.Umeng_IOS_AppKey,CommonConfig.Umeng_IOS_AppMaster_Secret);
		int long_token = token.length();
		String ticker = "教练审核";
		String title = "";
		String text = "";
		if(flag){
			title = "教练审核通过";
			text = "教练审核已通过,点击查看,并完善教练资料.";
		}else{
			title = "教练审核拒绝";
			text = "教练审核已拒绝,若有疑问,请联系客服.";
		}
		String after_open = "go_app";
		String display_type = "notification";
		if(long_token == CommonConfig.Umeng_Android_Byte){
			pushAD.sendAndroidUnicast(token, ticker, title, text, after_open, display_type, production_mode, null, null);
		}else if(long_token == CommonConfig.Umeng_IOS_Byte){
			pushIOS.sendIOSUnicast(token, text, production_mode, null, null);
		}
	}
	
	/**
	 * 发送教练申请课程的审核结果
	 * @param token
	 * @param flag
	 * @throws Exception
	 */
	public static void sendVerifyCourse(String token,boolean flag)throws Exception{
		PushUtil pushAD = new PushUtil(CommonConfig.Umeng_AD_AppKey,CommonConfig.Umeng_AD_AppMaster_Secret);
		PushUtil pushIOS = new PushUtil(CommonConfig.Umeng_IOS_AppKey,CommonConfig.Umeng_IOS_AppMaster_Secret);
		int long_token = token.length();
		String ticker = "教练申请开通课程";
		String title = "";
		String text = "";
		if(flag){
			title = "课程审核通过";
			text = "课程审核已通过,点击查看.";
		}else{
			title = "课程审核拒绝";
			text = "课程审核已拒绝,若有疑问,请联系客服.";
		}
		String after_open = "go_app";
		String display_type = "notification";
		if(long_token == CommonConfig.Umeng_Android_Byte){
			pushAD.sendAndroidUnicast(token, ticker, title, text, after_open, display_type, production_mode, null, null);
		}else if(long_token == CommonConfig.Umeng_IOS_Byte){
			pushIOS.sendIOSUnicast(token, text, production_mode, null, null);
		}
	}

	/**
	 * 广播
	 * @param po
	 * @throws Exception
	 */
	public static String sendBroadcast(PushOption po)throws Exception{
		PushUtil pushAD = new PushUtil(CommonConfig.Umeng_AD_AppKey,CommonConfig.Umeng_AD_AppMaster_Secret);
		PushUtil pushIOS = new PushUtil(CommonConfig.Umeng_IOS_AppKey,CommonConfig.Umeng_IOS_AppMaster_Secret);
		Map<String,String> kMap;
		Map<String,String> eMap = Extra(po.getExtra());
		kMap = androidOption(po);
		boolean ad = pushAD.sendAndroidBroadcast(po.getTicker(), po.getTitle(), po.getText(), po.getAfter_open(), po.getDisplay_type(), production_mode, kMap, eMap);
		kMap = iosOption(po);
		boolean ios = pushIOS.sendIOSBroadcast(po.getText(), production_mode, kMap, eMap);
		String result = CommonConfig.Broadcast_Success;
		if(!ad){
			result = CommonConfig.Broadcast_Error_Andrid;
		}
		if(!ios){
			result = CommonConfig.Broadcast_Error_IOS;
		}
		if(!ad&&!ios){
			result = CommonConfig.Broadcast_Error;
		}
		return result;
	}

	/**
	 * 单播
	 * @param po
	 * @throws Exception
	 */
	public static String sendUnicast(PushOption po)throws Exception{
		PushUtil pushAD = new PushUtil(CommonConfig.Umeng_AD_AppKey,CommonConfig.Umeng_AD_AppMaster_Secret);
		PushUtil pushIOS = new PushUtil(CommonConfig.Umeng_IOS_AppKey,CommonConfig.Umeng_IOS_AppMaster_Secret);
		int long_token = po.getDevice_tokens().length();
		Map<String,String> kMap;
		Map<String,String> eMap = Extra(po.getExtra());
		boolean ad = false;
		boolean ios = false;
		if(long_token == CommonConfig.Umeng_Android_Byte){
			kMap = androidOption(po);
			ad = pushAD.sendAndroidUnicast(po.getDevice_tokens(), po.getTicker(), po.getTitle(), po.getText(), po.getAfter_open(), po.getDisplay_type(), production_mode, kMap, eMap);
		}else if(long_token == CommonConfig.Umeng_IOS_Byte){
			kMap = iosOption(po);
			ios = pushIOS.sendIOSUnicast(po.getDevice_tokens(), po.getText(), production_mode, kMap, eMap);
		}
		String result = CommonConfig.Unicast_Success;
		if(!ad){
			result = CommonConfig.Unicast_Error_Android;
		}
		if(!ios){
			result = CommonConfig.Unicast_Error_IOS;
		}
		if(!ad&&!ios){
			result = CommonConfig.Unicast_Error;
		}
		return result;
	}

	/**
	 * 列播
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public static String sendListcast(PushOption po)throws Exception{
		PushUtil pushAD = new PushUtil(CommonConfig.Umeng_AD_AppKey,CommonConfig.Umeng_AD_AppMaster_Secret);
		PushUtil pushIOS = new PushUtil(CommonConfig.Umeng_IOS_AppKey,CommonConfig.Umeng_IOS_AppMaster_Secret);
		int long_token = po.getDevice_tokens().length();
		Map<String,String> kMap;
		Map<String,String> eMap = Extra(po.getExtra());
		boolean ad = false;
		boolean ios = false;
		if(long_token == CommonConfig.Umeng_Android_Byte){
			kMap = androidOption(po);
			ad = pushAD.sendAndroidListcast(po.getDevice_tokens(), po.getTicker(), po.getTitle(), po.getText(), po.getAfter_open(), po.getDisplay_type(), production_mode, kMap, eMap);
		}else if(long_token == CommonConfig.Umeng_IOS_Byte){
			kMap = iosOption(po);
			ios = pushIOS.sendIOSListcast(po.getDevice_tokens(), po.getText(), production_mode, kMap, eMap);
		}
		String result = CommonConfig.Unicast_Success;
		if(!ad){
			result = CommonConfig.Unicast_Error_Android;
		}
		if(!ios){
			result = CommonConfig.Unicast_Error_IOS;
		}
		if(!ad&&!ios){
			result = CommonConfig.Unicast_Error;
		}
		return result;
	}
	
	/**
	 * 批量发送
	 * @param tokens
	 * @param po
	 * @throws Exception
	 */
	public static String sendFilecast(String[] tokens,PushOption po)throws Exception{
		PushUtil pushAD = new PushUtil(CommonConfig.Umeng_AD_AppKey,CommonConfig.Umeng_AD_AppMaster_Secret);
		PushUtil pushIOS = new PushUtil(CommonConfig.Umeng_IOS_AppKey,CommonConfig.Umeng_IOS_AppMaster_Secret);
		String result = CommonConfig.Filecast_Success;
		if(tokens!=null&&tokens.length>0){
			Collection<String> androidToken = new ArrayList<String>();
			Collection<String> iosToken = new ArrayList<String>();
			int long_token = 0;
			Map<String,String> kMap;
			Map<String,String> eMap = Extra(po.getExtra());
			boolean ad = false;
			boolean ios = false;
			for(String token : tokens){
				long_token = token.length();
				if(long_token == CommonConfig.Umeng_Android_Byte){
					androidToken.add(token);
				}else if(long_token == CommonConfig.Umeng_IOS_Byte){
					iosToken.add(token);
				}
			}
			if(androidToken.size()>0){
				kMap = androidOption(po);
				ad = pushAD.sendAndroidFilecast(androidToken, po.getTicker(), po.getTitle(), po.getText(), po.getAfter_open(), po.getDisplay_type(), production_mode, kMap, eMap);
			}
			if(iosToken.size()>0){
				kMap = iosOption(po);
				ios = pushIOS.sendIOSFilecast(iosToken, po.getText(), production_mode, kMap, eMap);
			}
			if(!ad){
				result = CommonConfig.Filecast_Error_Android;
			}
			if(!ios){
				result = CommonConfig.Filecast_Error_IOS;
			}
			if(!ad&&!ios){
				result = CommonConfig.Filecast_Error;
			}
		}else{
			result = CommonConfig.Filecast_Error_Token;
		}
		return result;
	}

//	public static void sendGroupcast(JSONArray array, String ticker, String title, String text,
//			String after_open,String display_type,
//			Map<String,String> kMap,Map<String,String> eMap)throws Exception{
//		PushUtil push = new PushUtil(CommonConfig.Umeng_AppKey,CommonConfig.Umeng_AppMaster_Secret);
//		int long_token = token.length();
//		if(long_token == CommonConfig.Umeng_Android_Byte){
//			push.sendAndroidUnicast(token, ticker, title, text, after_open, display_type, production_mode, kMap, eMap);
//		}else if(long_token == CommonConfig.Umeng_IOS_Byte){
//			push.sendIOSUnicast(token, text, production_mode, kMap, eMap);
//		}
//	}

	/**
	 * 自定义字段
	 * @param extraStr json格式的字符串对象
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> Extra(String extraStr)throws Exception{
		Map<String,String> extraMap = new HashMap<String,String>();
		if(!StringUtils.isEmpty(extraStr)){
			JSONObject json = new JSONObject(extraStr);
			extraMap = (Map<String,String>) json;
		}
		return extraMap;
	}

	/**
	 * ios可选配置
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> iosOption(PushOption po)throws Exception{
		Map<String,String> result = new HashMap<String,String>();
		if(po!=null){
			if(!StringUtils.isEmpty(po.getStart_time())){
				result.put("start_time",po.getStart_time());
			}
			if(!StringUtils.isEmpty(po.getExpire_time())){
				result.put("expire_time",po.getExpire_time());
			}
			if(!StringUtils.isEmpty(po.getMax_send_num())){
				result.put("max_send_num",po.getMax_send_num());
			}
			if(!StringUtils.isEmpty(po.getDescription())){
				result.put("description",po.getDescription());
			}
		}
		return result;
	}

	/**
	 * 安卓设备可选配置
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> androidOption(PushOption po)throws Exception{
		Map<String,String> result = new HashMap<String,String>();
		if(po!=null){
			if(!StringUtils.isEmpty(po.getIcon())){
				result.put("icon",po.getIcon());
			}
			if(!StringUtils.isEmpty(po.getAfter_open())&&!po.getAfter_open().equalsIgnoreCase("go_app")){
				String key = "";
				String value = "";
				if(po.getAfter_open().equals(GoUrl)){
					if(!StringUtils.isEmpty(po.getUrl())){
						key = "url";
						value = po.getUrl();
					}
				}else if(po.getAfter_open().equals(GoActivity)){
					if(!StringUtils.isEmpty(po.getActivity())){
						key = "activity";
						value = po.getActivity();
					}
				}else if(po.getAfter_open().equals(GoCustom)){
					if(!StringUtils.isEmpty(po.getCustom())){
						key = "custom";
						value = po.getCustom();
					}
				}
				if(key!=""&&value!=""){
					result.put(key,value);
				}
			}
			if(!StringUtils.isEmpty(po.getBuilder_id())){
				result.put("builder_id",po.getBuilder_id());
			}
			if(!StringUtils.isEmpty(po.getDescription())){
				result.put("description",po.getDescription());
			}
			if(!StringUtils.isEmpty(po.getExpire_time())){
				result.put("expire_time",po.getExpire_time());
			}
			if(!StringUtils.isEmpty(po.getImg())){
				result.put("img",po.getImg());
			}
			if(!StringUtils.isEmpty(po.getLargeIcon())){
				result.put("largeIcon",po.getLargeIcon());
			}
			if(!StringUtils.isEmpty(po.getMax_send_num())){
				result.put("max_send_num",po.getMax_send_num());
			}
			if(!StringUtils.isEmpty(po.getOut_biz_no())){
				result.put("out_biz_no",po.getOut_biz_no());
			}
			if(!StringUtils.isEmpty(po.getPlay_lights())){
				result.put("play_lights",po.getPlay_lights());
			}
			if(!StringUtils.isEmpty(po.getPlay_sound())){
				result.put("play_sound",po.getPlay_sound());
			}
			if(!StringUtils.isEmpty(po.getSound())){
				result.put("sound",po.getSound());
			}
			if(!StringUtils.isEmpty(po.getStart_time())){
				result.put("start_time",po.getStart_time());
			}
		}
		return result;
	}
}
