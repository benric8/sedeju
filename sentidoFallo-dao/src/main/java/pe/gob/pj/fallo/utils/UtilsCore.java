package pe.gob.pj.fallo.utils;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/***
 * Utilitario
 * Neil Gilt
 * */
public class UtilsCore {



    /**
     * Returns MAC address of the given interface name.
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return  mac address or empty string
     */
    public static String getMACAddress() throws SocketException  {
        try {
        	Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        	while (networkInterfaces.hasMoreElements()) {
        		NetworkInterface networkInterface = networkInterfaces.nextElement();
        		byte[] mac = networkInterface.getHardwareAddress();
        		if (mac != null) {
        			StringBuilder macAddress = new StringBuilder();
        			for (int i = 0; i < mac.length; i++) {
        				macAddress.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        			}
        			return macAddress.toString();
        		}
        	}
        } catch (Exception ex) { } 
        return "";
    
    }

    /**
     * Get IP address from first non-localhost interface
     * @param ipv4  true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                                return delim<0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }

    public static String getPCName(){
        try {
            String computername=InetAddress.getLocalHost().getHostName();
            return computername;
        }catch (Exception e){

        }
        return "";
    }

}
