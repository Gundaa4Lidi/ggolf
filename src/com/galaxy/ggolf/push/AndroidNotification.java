package com.galaxy.ggolf.push;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Administrator on 2017-06-16.
 */
public class AndroidNotification extends UmengNotification {
    protected static final HashSet<String> PAYLOAD_KEYS = new HashSet(Arrays.asList(new String[]{"display_type"}));
    protected static final HashSet<String> BODY_KEYS = new HashSet(Arrays.asList(new String[]{"ticker", "title", "text", "builder_id", "icon", "largeIcon", "img", "play_vibrate", "play_lights", "play_sound", "sound", "after_open", "url", "activity", "custom"}));

    public AndroidNotification() {
    }

    public boolean setPredefinedKeyValue(String key, Object value) throws Exception {
        if(ROOT_KEYS.contains(key)) {
            this.rootJson.put(key, value);
        } else {
            JSONObject bodyJson;
            if(PAYLOAD_KEYS.contains(key)) {
                bodyJson = null;
                if(this.rootJson.has("payload")) {
                    bodyJson = this.rootJson.getJSONObject("payload");
                } else {
                    bodyJson = new JSONObject();
                    this.rootJson.put("payload", bodyJson);
                }

                bodyJson.put(key, value);
            } else if(BODY_KEYS.contains(key)) {
                bodyJson = null;
                JSONObject payloadJson = null;
                if(this.rootJson.has("payload")) {
                    payloadJson = this.rootJson.getJSONObject("payload");
                } else {
                    payloadJson = new JSONObject();
                    this.rootJson.put("payload", payloadJson);
                }

                if(payloadJson.has("body")) {
                    bodyJson = payloadJson.getJSONObject("body");
                } else {
                    bodyJson = new JSONObject();
                    payloadJson.put("body", bodyJson);
                }

                bodyJson.put(key, value);
            } else {
                if(!POLICY_KEYS.contains(key)) {
                    if(key != "payload" && key != "body" && key != "policy" && key != "extra") {
                        throw new Exception("Unknown key: " + key);
                    }

                    throw new Exception("You don't need to set value for " + key + " , just set values for the sub keys in it.");
                }

                bodyJson = null;
                if(this.rootJson.has("policy")) {
                    bodyJson = this.rootJson.getJSONObject("policy");
                } else {
                    bodyJson = new JSONObject();
                    this.rootJson.put("policy", bodyJson);
                }

                bodyJson.put(key, value);
            }
        }

        return true;
    }

    public boolean setExtraField(String key, String value) throws Exception {
        JSONObject payloadJson = null;
        JSONObject extraJson = null;
        if(this.rootJson.has("payload")) {
            payloadJson = this.rootJson.getJSONObject("payload");
        } else {
            payloadJson = new JSONObject();
            this.rootJson.put("payload", payloadJson);
        }

        if(payloadJson.has("extra")) {
            extraJson = payloadJson.getJSONObject("extra");
        } else {
            extraJson = new JSONObject();
            payloadJson.put("extra", extraJson);
        }

        extraJson.put(key, value);
        return true;
    }
}
