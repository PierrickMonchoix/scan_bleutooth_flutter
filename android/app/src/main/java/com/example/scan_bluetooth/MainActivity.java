package com.example.scan_bluetooth;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.net.wifi.*;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import io.flutter.embedding.android.FlutterActivity;
import java.util.*;

import android.bluetooth.*;

public class MainActivity extends FlutterActivity {

    private static final String CHANNEL = "string_id_channel";

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler((call, result) -> {
                    // Note: this method is invoked on the main thread.
                    if (call.method.equals("getWifiScan")) {
                        String stringListWifi = getListWifi().toString();
                        result.success(stringListWifi);
                    } else if (call.method.equals("getHelloWorld")) {
                        result.success("this is your hello world answer");
                    } else if (call.method.equals("getTestScan")) {
                        boolean okScan = getTestScan();
                        result.success(Boolean.toString(okScan));
                    } else if (call.method.equals("getListBle")) {
                        result.success(getListBle());
                    } else {
                        result.notImplemented();
                    }
                });
    }

    private String getListBle() {
        BluetoothManager bluetoothManager = (BluetoothManager) getApplicationContext()
                .getSystemService(BLUETOOTH_SERVICE);
        BluetoothAdapter adapter = bluetoothManager.getAdapter();

        Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                return deviceName;
                //String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }
        return("rien trouve");
    }

    private List<ScanResult> getListWifi() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        boolean okScan = wifiManager.startScan();
        List<ScanResult> results = wifiManager.getScanResults();
        return results;
    }

    private boolean getTestScan() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        boolean okScan = wifiManager.startScan();
        return okScan;
    }

}
