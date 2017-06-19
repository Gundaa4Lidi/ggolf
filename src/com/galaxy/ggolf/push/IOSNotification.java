package com.galaxy.ggolf.push;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Administrator on 2017-06-16.
 */
public class IOSNotification extends UmengNotification {
    protected static final HashSet<String> APS_KEYS = new HashSet(Arrays.asList(new String[]{"alert", "badge", "sound", "content-available"}));

    public IOSNotification() {
    }

    public boolean setPredefinedKeyValue(String key, Object value) throws Exception {
        if(ROOT_KEYS.contains(key)) {
            this.rootJson.put(key, value);
        } else {
            JSONObject apsJson;
            if(APS_KEYS.contains(key)) {
                apsJson = null;
                JSONObject payloadJson = null;
                if(this.rootJson.has("payload")) {
                    payloadJson = this.rootJson.getJSONObject("payload");
                } else {
                    payloadJson = new JSONObject();
                    this.rootJson.put("payload", payloadJson);
                }

                if(payloadJson.has("aps")) {
                    apsJson = payloadJson.getJSONObject("aps");
                } else {
                    apsJson = new JSONObject();
                    payloadJson.put("aps", apsJson);
                }

                apsJson.put(key, value);
            } else {
                if(!POLICY_KEYS.contains(key)) {
                    if(key != "payload" && key != "aps" && key != "policy") {
                        throw new Exception("Unknownd key: " + key);
                    }

                    throw new Exception("You don't need to set value for " + key + " , just set values for the sub keys in it.");
                }

                apsJson = null;
                if(this.rootJson.has("policy")) {
                    apsJson = this.rootJson.getJSONObject("policy");
                } else {
                    apsJson = new JSONObject();
                    this.rootJson.put("policy", apsJson);
                }

                apsJson.put(key, value);
            }
        }

        return true;
    }

    public boolean setCustomizedField(String key, String value) throws Exception {
        JSONObject payloadJson = null;
        if(this.rootJson.has("payload")) {
            payloadJson = this.rootJson.getJSONObject("payload");
        } else {
            payloadJson = new JSONObject();
            this.rootJson.put("payload", payloadJson);
        }

        payloadJson.put(key, value);
        return true;
    }
}
