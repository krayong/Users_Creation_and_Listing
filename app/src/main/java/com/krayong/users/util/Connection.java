package com.krayong.users.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.provider.Settings;

import androidx.fragment.app.Fragment;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Connection {
	public static void checkConnection(final Fragment fragment) {
		checkConnection(fragment.requireActivity());
	}
	
	public static void checkConnection(final Activity activity) {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(CONNECTIVITY_SERVICE);
		Network network = connectivityManager.getActiveNetwork();
		
		if (network == null || connectivityManager.getNetworkCapabilities(network) == null) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity)
					.setMessage("Active internet connection is required")
					.setCancelable(false)
					.setPositiveButton("Connect", (dialog, which) -> activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
					.setNegativeButton("Cancel", (dialog, which) -> {
						dialog.cancel();
						activity.finish();
					})
					.setNeutralButton("Reload", (dialog, which) -> {
						dialog.cancel();
						checkConnection(activity);
					});
			alertDialog.show();
		}
	}
}
