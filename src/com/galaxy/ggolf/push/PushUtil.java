package com.galaxy.ggolf.push;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.galaxy.ggolf.push.Android.AndroidBroadcast;
import com.galaxy.ggolf.push.Android.AndroidCustomizedcast;
import com.galaxy.ggolf.push.Android.AndroidFilecast;
import com.galaxy.ggolf.push.Android.AndroidGroupcast;
import com.galaxy.ggolf.push.Android.AndroidListCast;
import com.galaxy.ggolf.push.Android.AndroidUnicast;
import com.galaxy.ggolf.push.IOS.IOSBroadcast;
import com.galaxy.ggolf.push.IOS.IOSCustomizedcast;
import com.galaxy.ggolf.push.IOS.IOSFilecast;
import com.galaxy.ggolf.push.IOS.IOSGroupcast;
import com.galaxy.ggolf.push.IOS.IOSListcast;
import com.galaxy.ggolf.push.IOS.IOSUnicast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PushUtil {
	private String appkey;
	private String appMasterSecret;
	private String timestamp;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public PushUtil(String appkey,String secret) {
		try {
			this.appkey = appkey;
			this.appMasterSecret = secret;
			this.timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000L));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occured",e);
		}
	}
	
	/**
	 * 安卓设备广播推送
	 * @param ticker 			提示标识
	 * @param title				标题
	 * @param text				内容
	 * @param after_open		点击"通知"的后续行为，默认为打开app。
	 * @param display_type		消息类型，值可以为:
                                notification-通知，message-消息
	 * @param production_mode   正式/测试模式。测试模式下，只会将消息发给测试设备。
	 * @param keyValueMap		可选字段的键值对
	 * @param extraFieldMap		自定义字段的键值对
	 * @throws Exception
	 */
	public boolean sendAndroidBroadcast(String ticker,String title,String text,
			String after_open,String display_type,String production_mode,
			Map<String,String> keyValueMap,Map<String,String> extraFieldMap) throws Exception {
        AndroidBroadcast broadcast = new AndroidBroadcast();
        broadcast.setAppMasterSecret(this.appMasterSecret);
        broadcast.setPredefinedKeyValue("appkey", this.appkey);
        broadcast.setPredefinedKeyValue("timestamp", this.timestamp);
        broadcast.setPredefinedKeyValue("ticker", ticker);
        broadcast.setPredefinedKeyValue("title", title);
        broadcast.setPredefinedKeyValue("text", text);
        broadcast.setPredefinedKeyValue("after_open", after_open);
        broadcast.setPredefinedKeyValue("display_type", display_type);
        broadcast.setPredefinedKeyValue("production_mode", production_mode);
        if(!keyValueMap.isEmpty()){
        	for(String k : keyValueMap.keySet()){
        		broadcast.setPredefinedKeyValue(k, keyValueMap.get(k));
        	}
        }
        if(!extraFieldMap.isEmpty()){
        	for(String key : extraFieldMap.keySet()){
        		broadcast.setExtraField(key, extraFieldMap.get(key));
        	}
        }
//        broadcast.setExtraField("test", "helloworld");
        return broadcast.send();
    }

	/**
	 * 安卓设备单播推送
	 * @param token				设备的唯一标识
	 * @param ticker			提示标识
	 * @param title				标题
	 * @param text				内容
	 * @param after_open		点击"通知"的后续行为，默认为打开app。
	 * @param display_type		消息类型，值可以为:
                                notification-通知，message-消息
	 * @param production_mode	正式/测试模式。测试模式下，只会将消息发给测试设备。
	 * @param keyValueMap		可选字段的键值对
	 * @param extraFieldMap		自定义字段的键值对
	 * @throws Exception
	 */
    public boolean sendAndroidUnicast(String token,String ticker,String title,String text,
    		String after_open,String display_type,String production_mode,
    		Map<String,String> keyValueMap,Map<String,String> extraFieldMap) throws Exception {
        AndroidUnicast unicast = new AndroidUnicast();
        unicast.setAppMasterSecret(this.appMasterSecret);
        unicast.setPredefinedKeyValue("appkey", this.appkey);
        unicast.setPredefinedKeyValue("timestamp", this.timestamp);
        unicast.setPredefinedKeyValue("device_tokens", token);
        unicast.setPredefinedKeyValue("ticker", ticker);
        unicast.setPredefinedKeyValue("title", title);
        unicast.setPredefinedKeyValue("text", text);
        unicast.setPredefinedKeyValue("after_open", after_open);
        unicast.setPredefinedKeyValue("display_type", display_type);
        unicast.setPredefinedKeyValue("production_mode", production_mode);
        if(keyValueMap!=null){
        	for(String k : keyValueMap.keySet()){
        		unicast.setPredefinedKeyValue(k, keyValueMap.get(k));
        	}
        }
        if(extraFieldMap!=null){
        	for(String key : extraFieldMap.keySet()){
        		unicast.setExtraField(key, extraFieldMap.get(key));
        	}
        }
        return unicast.send();
    }
    
    /**
	 * 安卓设备列播推送
	 * @param token				设备的唯一标识(多个token用逗号隔开)
	 * @param ticker			提示标识
	 * @param title				标题
	 * @param text				内容
	 * @param after_open		点击"通知"的后续行为，默认为打开app。
	 * @param display_type		消息类型，值可以为:
                                notification-通知，message-消息
	 * @param production_mode	正式/测试模式。测试模式下，只会将消息发给测试设备。
	 * @param keyValueMap		可选字段的键值对
	 * @param extraFieldMap		自定义字段的键值对
	 * @throws Exception
	 */
    public boolean sendAndroidListcast(String tokens,String ticker,String title,String text,
    		String after_open,String display_type,String production_mode,
    		Map<String,String> keyValueMap,Map<String,String> extraFieldMap) throws Exception {
    	AndroidListCast listcast = new AndroidListCast();
        listcast.setAppMasterSecret(this.appMasterSecret);
        listcast.setPredefinedKeyValue("appkey", this.appkey);
        listcast.setPredefinedKeyValue("timestamp", this.timestamp);
        listcast.setPredefinedKeyValue("device_tokens", tokens);
        listcast.setPredefinedKeyValue("ticker", ticker);
        listcast.setPredefinedKeyValue("title", title);
        listcast.setPredefinedKeyValue("text", text);
        listcast.setPredefinedKeyValue("after_open", after_open);
        listcast.setPredefinedKeyValue("display_type", display_type);
        listcast.setPredefinedKeyValue("production_mode", production_mode);
        if(keyValueMap!=null){
        	for(String k : keyValueMap.keySet()){
        		listcast.setPredefinedKeyValue(k, keyValueMap.get(k));
        	}
        }
        if(extraFieldMap!=null){
        	for(String key : extraFieldMap.keySet()){
        		listcast.setExtraField(key, extraFieldMap.get(key));
        	}
        }
        return listcast.send();
    }

    /**
     * 安卓设备组播推送
     * @param tagArray          过滤条件的集合
     * @param ticker            提示标识
     * @param title             标题
     * @param text              内容
     * @param after_open        点击"通知"的后续行为，默认为打开app。
     * @param display_type      消息类型，值可以为:
     *                          notification-通知，message-消息
     * @param production_mode   正式/测试模式。测试模式下，只会将消息发给测试设备。
     * @param keyValueMap       可选字段的键值对
     * @param extraFieldMap     自定义字段的键值对
     * @throws Exception
     */
    public boolean sendAndroidGroupcast(JSONArray tagArray,String ticker,String title,String text,
    		String after_open,String display_type,String production_mode,
    		Map<String,String> keyValueMap,Map<String,String> extraFieldMap) throws Exception {
        AndroidGroupcast groupcast = new AndroidGroupcast();
        groupcast.setAppMasterSecret(this.appMasterSecret);
        groupcast.setPredefinedKeyValue("appkey", this.appkey);
        groupcast.setPredefinedKeyValue("timestamp", this.timestamp);
        JSONObject filterJson = new JSONObject();
        JSONObject whereJson = new JSONObject();
//        JSONArray tagArray = new JSONArray();
//        JSONObject testTag = new JSONObject();
//        JSONObject TestTag = new JSONObject();
//        testTag.put("tag", "test");
//        TestTag.put("tag", "Test");
//        tagArray.put(testTag);
//        tagArray.put(TestTag);
        whereJson.put("and", tagArray);
        filterJson.put("where", whereJson);
        System.out.println(filterJson.toString());
        groupcast.setPredefinedKeyValue("filter", filterJson);
        groupcast.setPredefinedKeyValue("ticker", ticker);
        groupcast.setPredefinedKeyValue("title", title);
        groupcast.setPredefinedKeyValue("text", text);
        groupcast.setPredefinedKeyValue("after_open", after_open);
        groupcast.setPredefinedKeyValue("display_type", display_type);
        groupcast.setPredefinedKeyValue("production_mode", production_mode);
        if(keyValueMap!=null){
        	for(String k : keyValueMap.keySet()){
        		groupcast.setPredefinedKeyValue(k, keyValueMap.get(k));
        	}
        }
        if(extraFieldMap!=null){
        	for(String key : extraFieldMap.keySet()){
        		groupcast.setExtraField(key, extraFieldMap.get(key));
        	}
        }
        return groupcast.send();
    }

    /**
     *  安卓设备自定义推送
     * @param alias             当type=customizedcast时, 开发者填写自己的alias。
                            	要求不超过50个alias,多个alias以英文逗号间隔。
                            	在SDK中调用setAlias(alias, alias_type)时所设置的alias
     * @param alias_type        当type=customizedcast时，必填，alias的类型,
                                alias_type可由开发者自定义,开发者在SDK中
                            	调用setAlias(alias, alias_type)时所设置的alias_type
     * @param ticker            提示标识
     * @param title             标题
     * @param text              内容
     * @param after_open        点击"通知"的后续行为，默认为打开app。
     * @param display_type      消息类型，值可以为:
     *                          notification-通知，message-消息
     * @param production_mode   正式/测试模式。测试模式下，只会将消息发给测试设备。
     * @param keyValueMap       可选字段的键值对
     * @param extraFieldMap     自定义字段的键值对
     * @throws Exception
     */
    public boolean sendAndroidCustomizedcast(String alias, String alias_type, String ticker, String title,
    		String text, String after_open, String display_type,String production_mode,
    		Map<String,String> keyValueMap, Map<String,String> extraFieldMap) throws Exception {
        AndroidCustomizedcast customizedcast = new AndroidCustomizedcast();
        customizedcast.setAppMasterSecret(this.appMasterSecret);
        customizedcast.setPredefinedKeyValue("appkey", this.appkey);
        customizedcast.setPredefinedKeyValue("timestamp", this.timestamp);
        customizedcast.setPredefinedKeyValue("alias", alias);
        customizedcast.setPredefinedKeyValue("alias_type", alias_type);
        customizedcast.setPredefinedKeyValue("ticker", ticker);
        customizedcast.setPredefinedKeyValue("title", title);
        customizedcast.setPredefinedKeyValue("text", text);
        customizedcast.setPredefinedKeyValue("after_open", after_open);
        customizedcast.setPredefinedKeyValue("display_type", display_type);
        customizedcast.setPredefinedKeyValue("production_mode", production_mode);
        if(keyValueMap!=null){
        	for(String k : keyValueMap.keySet()){
        		customizedcast.setPredefinedKeyValue(k, keyValueMap.get(k));
        	}
        }
        if(extraFieldMap!=null){
        	for(String key : extraFieldMap.keySet()){
        		customizedcast.setExtraField(key, extraFieldMap.get(key));
        	}
        }
        return customizedcast.send();
    }

    /**
     * 安卓设备文件推送(先通过文件上传接口获取文件id,再通过文件方式发送消息)
     * @param tokens            设备唯一标识的集合
     * @param ticker            提示标识
     * @param title             标题
     * @param text              内容
     * @param after_open        点击"通知"的后续行为，默认为打开app。
     * @param display_type      消息类型，值可以为:
     *                          notification-通知，message-消息
     * @param keyValueMap       可选字段的键值对
     * @param extraFieldMap     自定义字段的键值对
     * @throws Exception
     */
    public boolean sendAndroidFilecast(Collection<String> tokens, String ticker, String title,
    		String text, String after_open, String display_type,String production_mode,
    		Map<String,String> keyValueMap, Map<String,String> extraFieldMap) throws Exception {
        AndroidFilecast filecast = new AndroidFilecast();
        filecast.setAppMasterSecret(this.appMasterSecret);
        filecast.setPredefinedKeyValue("appkey", this.appkey);
        filecast.setPredefinedKeyValue("timestamp", this.timestamp);
        StringBuffer sb = new StringBuffer();
        for(String token : tokens){
        	sb.append(token);
        	sb.append("\n");
        }
        String fileContents = sb.toString();
        filecast.uploadContents(fileContents);
        filecast.setPredefinedKeyValue("ticker", ticker);
        filecast.setPredefinedKeyValue("title", title);
        filecast.setPredefinedKeyValue("text", text);
        filecast.setPredefinedKeyValue("after_open", after_open);
        filecast.setPredefinedKeyValue("display_type", display_type);
        filecast.setPredefinedKeyValue("production_mode",production_mode);
        if(keyValueMap!=null){
        	for(String k : keyValueMap.keySet()){
        		filecast.setPredefinedKeyValue(k, keyValueMap.get(k));
        	}
        }
        if(extraFieldMap!=null){
        	for(String key : extraFieldMap.keySet()){
        		filecast.setExtraField(key, extraFieldMap.get(key));
        	}
        }
        return filecast.send();
    }

    /**
     * IOS设备广播推送
     * @param alert             内容
     * @param production_mode   正式/测试模式。测试模式下，只会将消息发给测试设备。
     * @param keyValueMap       可选字段的键值对
     * @param extraFieldMap     自定义字段的键值对
     * @throws Exception
     */
    public boolean sendIOSBroadcast(String alert, String production_mode,
    		Map<String,String> keyValueMap, Map<String,String> extraFieldMap) throws Exception {
        IOSBroadcast broadcast = new IOSBroadcast();
        broadcast.setAppMasterSecret(this.appMasterSecret);
        broadcast.setPredefinedKeyValue("appkey", this.appkey);
        broadcast.setPredefinedKeyValue("timestamp", this.timestamp);
        broadcast.setPredefinedKeyValue("alert", alert);
        broadcast.setPredefinedKeyValue("badge", Integer.valueOf(0));
        broadcast.setPredefinedKeyValue("sound", "chime");
        broadcast.setPredefinedKeyValue("production_mode", production_mode);
        if(keyValueMap!=null){
        	for(String k : keyValueMap.keySet()){
        		broadcast.setPredefinedKeyValue(k, keyValueMap.get(k));
        	}
        }
        if(extraFieldMap!=null){
        	for(String key : extraFieldMap.keySet()){
        		broadcast.setCustomizedField(key, extraFieldMap.get(key));
        	}
        }
//        broadcast.setCustomizedField("test", "helloworld");
        return broadcast.send();
    }

    /**
     * IOS设备单播推送
     * @param device_tokens     设备唯一标识
     * @param alert             内容
     * @param production_mode   正式/测试模式。测试模式下，只会将消息发给测试设备。
     * @param keyValueMap       可选字段的键值对
     * @param extraFieldMap     自定义字段的键值对
     * @throws Exception
     */
    public boolean sendIOSUnicast(String device_tokens, String alert, String production_mode,
    		Map<String,String> keyValueMap, Map<String,String> extraFieldMap) throws Exception {
        IOSUnicast unicast = new IOSUnicast();
        unicast.setAppMasterSecret(this.appMasterSecret);
        unicast.setPredefinedKeyValue("appkey", this.appkey);
        unicast.setPredefinedKeyValue("timestamp", this.timestamp);
        unicast.setPredefinedKeyValue("device_tokens", device_tokens);
        unicast.setPredefinedKeyValue("alert", alert);
        unicast.setPredefinedKeyValue("badge", Integer.valueOf(0));
        unicast.setPredefinedKeyValue("sound", "chime");
        unicast.setPredefinedKeyValue("production_mode", production_mode);
        if(keyValueMap!=null){
        	for(String k : keyValueMap.keySet()){
        		unicast.setPredefinedKeyValue(k, keyValueMap.get(k));
        	}
        }
        if(extraFieldMap!=null){
        	for(String key : extraFieldMap.keySet()){
        		unicast.setCustomizedField(key, extraFieldMap.get(key));
        	}
        }
        return unicast.send();
    }
    
    /**
     * IOS设备列播推送
     * @param device_tokens     设备唯一标识(多个token用逗号隔开)
     * @param alert             内容
     * @param production_mode   正式/测试模式。测试模式下，只会将消息发给测试设备。
     * @param keyValueMap       可选字段的键值对
     * @param extraFieldMap     自定义字段的键值对
     * @throws Exception
     */
    public boolean sendIOSListcast(String device_tokens, String alert, String production_mode,
    		Map<String,String> keyValueMap, Map<String,String> extraFieldMap) throws Exception {
        IOSListcast listcast = new IOSListcast();
        listcast.setAppMasterSecret(this.appMasterSecret);
        listcast.setPredefinedKeyValue("appkey", this.appkey);
        listcast.setPredefinedKeyValue("timestamp", this.timestamp);
        listcast.setPredefinedKeyValue("device_tokens", device_tokens);
        listcast.setPredefinedKeyValue("alert", alert);
        listcast.setPredefinedKeyValue("badge", Integer.valueOf(0));
        listcast.setPredefinedKeyValue("sound", "chime");
        listcast.setPredefinedKeyValue("production_mode", production_mode);
        if(keyValueMap!=null){
        	for(String k : keyValueMap.keySet()){
        		listcast.setPredefinedKeyValue(k, keyValueMap.get(k));
        	}
        }
        if(extraFieldMap!=null){
        	for(String key : extraFieldMap.keySet()){
        		listcast.setCustomizedField(key, extraFieldMap.get(key));
        	}
        }
        return listcast.send();
    }

    /**
     * IOS设备组播推送
     * @param tagArray          过滤条件的集合
     * @param alert             内容
     * @param production_mode   正式/测试模式。测试模式下，只会将消息发给测试设备。
     * @param keyValueMap       可选字段的键值对
     * @param extraFieldMap     自定义字段的键值对
     * @throws Exception
     */
    public boolean sendIOSGroupcast(JSONArray tagArray, String alert, String production_mode,
    		Map<String,String> keyValueMap, Map<String,String> extraFieldMap) throws Exception {
        IOSGroupcast groupcast = new IOSGroupcast();
        groupcast.setAppMasterSecret(this.appMasterSecret);
        groupcast.setPredefinedKeyValue("appkey", this.appkey);
        groupcast.setPredefinedKeyValue("timestamp", this.timestamp);
        JSONObject filterJson = new JSONObject();
        JSONObject whereJson = new JSONObject();
//        JSONArray tagArray = new JSONArray();
//        JSONObject testTag = new JSONObject();
//        testTag.put("tag", "iostest");
//        tagArray.put(testTag);
        whereJson.put("and", tagArray);
        filterJson.put("where", whereJson);
        System.out.println(filterJson.toString());
        groupcast.setPredefinedKeyValue("filter", filterJson);
        groupcast.setPredefinedKeyValue("alert", alert);
        groupcast.setPredefinedKeyValue("badge", Integer.valueOf(0));
        groupcast.setPredefinedKeyValue("sound", "chime");
        groupcast.setPredefinedKeyValue("production_mode", production_mode);
        if(keyValueMap!=null){
        	for(String k : keyValueMap.keySet()){
        		groupcast.setPredefinedKeyValue(k, keyValueMap.get(k));
        	}
        }
        if(extraFieldMap!=null){
        	for(String key : extraFieldMap.keySet()){
        		groupcast.setCustomizedField(key, extraFieldMap.get(key));
        	}
        }
        return groupcast.send();
    }

    /**
     * IOS自定义推送
     * @param alias             当type=customizedcast时, 开发者填写自己的alias。
     *                          要求不超过50个alias,多个alias以英文逗号间隔。
     *                          在SDK中调用setAlias(alias, alias_type)时所设置的alias
     * @param alias_type        当type=customizedcast时，必填，alias的类型,
     *                          alias_type可由开发者自定义,开发者在SDK中
     *                          调用setAlias(alias, alias_type)时所设置的alias_type
     * @param alert             内容
     * @param production_mode   正式/测试模式。测试模式下，只会将消息发给测试设备。
     * @param keyValueMap       可选字段的键值对
     * @param extraFieldMap     自定义字段的键值对
     * @throws Exception
     */
    public boolean sendIOSCustomizedcast(String alias, String alias_type, String alert, String production_mode,
    		Map<String,String> keyValueMap, Map<String,String> extraFieldMap) throws Exception {
        IOSCustomizedcast customizedcast = new IOSCustomizedcast();
        customizedcast.setAppMasterSecret(this.appMasterSecret);
        customizedcast.setPredefinedKeyValue("appkey", this.appkey);
        customizedcast.setPredefinedKeyValue("timestamp", this.timestamp);
        customizedcast.setPredefinedKeyValue("alias", alias);
        customizedcast.setPredefinedKeyValue("alias_type", alias_type);
        customizedcast.setPredefinedKeyValue("alert", alert);
        customizedcast.setPredefinedKeyValue("badge", Integer.valueOf(0));
        customizedcast.setPredefinedKeyValue("sound", "chime");
        customizedcast.setPredefinedKeyValue("production_mode", production_mode);
        if(keyValueMap!=null){
        	for(String k : keyValueMap.keySet()){
        		customizedcast.setPredefinedKeyValue(k, keyValueMap.get(k));
        	}
        }
        if(extraFieldMap!=null){
        	for(String key : extraFieldMap.keySet()){
        		customizedcast.setCustomizedField(key, extraFieldMap.get(key));
        	}
        }
        return customizedcast.send();
    }

    /**
     * 安卓设备文件推送(先通过文件上传接口获取文件id,再通过文件方式发送消息)
     * @param tokens            设备唯一标识的集合
     * @param alert             内容
     * @param production_mode   正式/测试模式。测试模式下，只会将消息发给测试设备。
     * @param keyValueMap       可选字段的键值对
     * @param extraFieldMap     自定义字段的键值对
     * @throws Exception
     */
    public boolean sendIOSFilecast(Collection<String> tokens, String alert, String production_mode,
    		Map<String,String> keyValueMap, Map<String,String> extraFieldMap) throws Exception {
        IOSFilecast filecast = new IOSFilecast();
        filecast.setAppMasterSecret(this.appMasterSecret);
        filecast.setPredefinedKeyValue("appkey", this.appkey);
        filecast.setPredefinedKeyValue("timestamp", this.timestamp);
        StringBuffer sb = new StringBuffer();
        for(String token : tokens){
        	sb.append(token);
        	sb.append("\n");
        }
        String fileContents = sb.toString();
        filecast.uploadContents(fileContents);
        filecast.setPredefinedKeyValue("alert", alert); 
        filecast.setPredefinedKeyValue("badge", Integer.valueOf(0));
        filecast.setPredefinedKeyValue("sound", "chime");
        filecast.setPredefinedKeyValue("production_mode", production_mode);
        if(keyValueMap!=null){
        	for(String k : keyValueMap.keySet()){
        		filecast.setPredefinedKeyValue(k, keyValueMap.get(k));
        	}
        }
        if(extraFieldMap!=null){
        	for(String key : extraFieldMap.keySet()){
        		filecast.setCustomizedField(key, extraFieldMap.get(key));
        	}
        }
        return filecast.send();
    }
}
