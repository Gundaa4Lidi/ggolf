package com.galaxy.ggolf.push.Android;

import com.galaxy.ggolf.push.AndroidNotification;

/**
 * Created by Administrator on 2017-06-16.
 */
public class AndroidGroupcast extends AndroidNotification {
    public AndroidGroupcast() {
        try {
            this.setPredefinedKeyValue("type", "groupcast");
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(1);
        }

    }
}
