package com.galaxy.ggolf.push.IOS;

import push.IOSNotification;

/**
 * Created by Administrator on 2017-06-16.
 */
public class IOSUnicast extends IOSNotification {
    public IOSUnicast() {
        try {
            this.setPredefinedKeyValue("type", "unicast");
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(1);
        }

    }
}
