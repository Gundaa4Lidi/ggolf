package com.galaxy.ggolf.push.Android;

import com.galaxy.ggolf.push.AndroidNotification;

public class AndroidListCast extends AndroidNotification {
	public AndroidListCast() {
		try {
            this.setPredefinedKeyValue("type", "listcast");
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(1);
        }
	}

}
