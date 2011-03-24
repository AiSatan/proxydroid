package org.proxydroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class ProxyDroidReceiver extends BroadcastReceiver {

	private String proxy;
	private String proxyType;
	private int port;
	private boolean isAutoStart = false;
	private boolean isInstalled = false;

	@Override
	public void onReceive(Context context, Intent intent) {

		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);

		isAutoStart = settings.getBoolean("isAutoStart", false);
		isInstalled = settings.getBoolean("isInstalled", false);

		if (isAutoStart && isInstalled) {
			proxy = settings.getString("proxy", "");
			proxyType = settings.getString("proxyType", "GAppProxy");
			String portText = settings.getString("port", "");
			if (portText != null && portText.length() > 0) {
				port = Integer.valueOf(portText);
				if (port <= 1024)
					port = 1984;
			} else {
				port = 1984;
			}
			
			Intent it = new Intent(context, ProxyDroidService.class);
			Bundle bundle = new Bundle();
			bundle.putString("proxy", proxy);
			bundle.putString("proxyType", proxyType);
			bundle.putInt("port", port);

			it.putExtras(bundle);
			context.startService(it);
		}
	}

}
