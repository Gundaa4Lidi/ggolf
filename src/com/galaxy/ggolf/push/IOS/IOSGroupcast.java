package com.galaxy.ggolf.push.IOS;

import com.galaxy.ggolf.push.IOSNotification;

/**
 * Created by Administrator on 2017-06-16.
 */
public class IOSGroupcast extends IOSNotification {
    public IOSGroupcast() {
        try {
            this.setPredefinedKeyValue("type", "groupcast");
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(1);
        }

    }
}
