package com.galaxy.ggolf.push.Android;

import com.galaxy.ggolf.push.AndroidNotification;

/**
 * Created by Administrator on 2017-06-16.
 */
public class AndroidUnicast extends AndroidNotification {
    public AndroidUnicast() {
        try {
            this.setPredefinedKeyValue("type", "unicast");
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(1);
        }

    }
}
