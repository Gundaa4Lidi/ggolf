package com.galaxy.ggolf.push.Android;

import com.galaxy.ggolf.push.AndroidNotification;

/**
 * Created by Administrator on 2017-06-16.
 */
public class AndroidBroadcast extends AndroidNotification {
    public AndroidBroadcast() {
        try {
            this.setPredefinedKeyValue("type", "broadcast");
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(1);
        }

    }
}
