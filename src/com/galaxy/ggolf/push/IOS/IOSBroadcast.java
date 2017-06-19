package com.galaxy.ggolf.push.IOS;

import com.galaxy.ggolf.push.IOSNotification;

/**
 * Created by Administrator on 2017-06-16.
 */
public class IOSBroadcast extends IOSNotification {
    public IOSBroadcast() {
        try {
            this.setPredefinedKeyValue("type", "broadcast");
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(1);
        }

    }
}
