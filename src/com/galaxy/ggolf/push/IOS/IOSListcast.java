package com.galaxy.ggolf.push.IOS;

import com.galaxy.ggolf.push.IOSNotification;

public class IOSListcast extends IOSNotification {
	public IOSListcast() {
		try {
            this.setPredefinedKeyValue("type", "listcast");
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(1);
        }
	}
}
