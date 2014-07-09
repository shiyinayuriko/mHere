package com.mHere.wifibackground.wificontrol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

public class WifiScanner implements Serializable {
	private static final long serialVersionUID = -8057239461080253786L;
	private WifiManager wifiManager;// 声明管理对象
    
	private WifiInfo wifiInfo;// Wifi信息
    private List<ScanResult> scanResultList; // 扫描出来的网络连接列表
    private List<WifiConfiguration> wifiConfigList;// 网络配置列表
    
    private WifiLock wifiLock;// Wifi锁
    
    public enum WifiCipherType {
        WIFICIPHER_WEP, WIFICIPHER_WPA, WIFICIPHER_NOPASS, WIFICIPHER_INVALID
    }
    public WifiScanner(Context context,String wifilock) {
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        startScan();
        wifiLock = wifiManager.createWifiLock(wifilock); // 创建一个锁的标志
    }
    
    public boolean getWifiStatus(){
         return wifiManager.isWifiEnabled();
    }
    public boolean openWifi() {
    	 return wifiManager.setWifiEnabled(true);
    }
    public boolean closeWifi() {
    	return wifiManager.setWifiEnabled(false);
    }

    // 锁定/解锁wifi
    public void lockWifi() {
        if (!wifiLock.isHeld()) {
            wifiLock.acquire(); 
        }
    }
    public void unLockWifi() {
        if (wifiLock.isHeld()) {
            wifiLock.release(); 
        }
    }

    // 扫描网络
    public WifiScanner startScan() {
        wifiManager.startScan();
        scanResultList = wifiManager.getScanResults(); // 扫描返回结果列表
        wifiConfigList = wifiManager.getConfiguredNetworks(); // 扫描配置列表
        wifiInfo = wifiManager.getConnectionInfo();
        return this;
    }

    public List<ScanResult> getScanResultList() {
        return scanResultList == null ? new ArrayList<ScanResult>():scanResultList;
    }
    public List<WifiConfiguration> getWifiConfigList() {
        return wifiConfigList== null ? new ArrayList<WifiConfiguration>():wifiConfigList;
    }
    public WifiInfo getWifiInfo(){
		return wifiInfo;
    }
    
    //获取指定信号的强度
    public int getLevel(int NetId)
    {
        return scanResultList.get(NetId).level;
    }
    
    // 添加一个连接
    public boolean addNetWordLink(String SSID, String password, WifiCipherType type) {
    	Log.i(SSID+password+type,"aaa");
    	WifiConfiguration config = createWifiInfo(SSID, password, type);
    	if(config == null) return false;
    	WifiConfiguration tempConfig = this.isExsits(SSID);
    	if (tempConfig != null) {
            wifiManager.removeNetwork(tempConfig.networkId);
    	}
        int NetId = wifiManager.addNetwork(config);
    	return wifiManager.enableNetwork(NetId, true);
    }
    public boolean linkNetWordLink(int NetId) {
        return wifiManager.enableNetwork(NetId, true);
    }
    
    // 禁用一个链接
    public boolean disableNetWordLink(int NetId) {
        wifiManager.disableNetwork(NetId);
        return wifiManager.disconnect();
    }
    // 移除一个链接
    public boolean removeNetworkLink(int NetId) {
        return wifiManager.removeNetwork(NetId);
    }
    
//    //不显示SSID
//    public void hiddenSSID(int NetId)
//    {
//        wifiConfigList.get(NetId).hiddenSSID=true;
//    }
//    //显示SSID
//    public void displaySSID(int NetId)
//    {
//        wifiConfigList.get(NetId).hiddenSSID=false;
//    }
    
    public WifiConfiguration createWifiInfo(String SSID, String password, WifiCipherType type) {
    WifiConfiguration config = new WifiConfiguration();
    
    config.allowedAuthAlgorithms.clear();
    config.allowedGroupCiphers.clear();
    config.allowedKeyManagement.clear();
    config.allowedPairwiseCiphers.clear();
    config.allowedProtocols.clear();
    config.SSID = "\"" + SSID + "\"";

    if (type == WifiCipherType.WIFICIPHER_NOPASS) {
    	config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        config.status = WifiConfiguration.Status.ENABLED;
    }else if (type == WifiCipherType.WIFICIPHER_WEP) {
    	config.wepKeys[0]  = "\"" + password + "\"";
        config.hiddenSSID = true;
        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        config.wepTxKeyIndex = 0;
    }else if (type == WifiCipherType.WIFICIPHER_WPA) {
        config.preSharedKey = "\"" + password + "\"";
        config.hiddenSSID = true;
//            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        
        config.status = WifiConfiguration.Status.ENABLED;
    } else {
            return null;
    }
    return config;
}
    
    private WifiConfiguration isExsits(String SSID) {
    	List<WifiConfiguration> wifiConfigList = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : wifiConfigList) {
                if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                        return existingConfig;
                }
        }
        return null;
    }
}