package com.github.stazxr.muses.utils.base.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 本地网络工具类，用于获取主机信息、IP地址、MAC地址等。
 *
 * @author SunTao
 * @since 2024-05-05
 */
public final class LocalHostUtil {
    private LocalHostUtil() {
    }

    /**
     * 获取本地主机名称。
     *
     * @return 本地主机名称
     * @throws UnknownHostException 如果无法解析本地主机名称为地址
     */
    public static String getLocalHostName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }

    /**
     * 获取本地主机地址。
     *
     * @return 本地主机地址
     * @throws UnknownHostException 如果无法解析本地主机名称为地址
     */
    public static String getLocalHostAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    /**
     * 获取当前机器的IP地址。
     *
     * @return 当前机器的IP地址
     * @throws SocketException      如果发生I/O错误
     * @throws UnknownHostException 如果无法解析本地主机名称为地址
     */
    public static String getLocalIp() throws SocketException, UnknownHostException {
        // 遍历所有的网络接口
        InetAddress candidateAddress = null;
        for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements();) {
            NetworkInterface networkInterface = interfaces.nextElement();
            for (Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses(); inetAddresses.hasMoreElements();) {
                InetAddress address = inetAddresses.nextElement();
                if (address.isLoopbackAddress() && address.getHostAddress().indexOf(':') != -1) {
                    // 排除回环地址和 IPv6 地址
                    continue;
                }

                if (address.isSiteLocalAddress()) {
                    // 如果是站点本地地址，直接返回
                    return address.getHostAddress();
                } else if (candidateAddress == null) {
                    // 站点本地地址未被发现，先记录候选地址
                    candidateAddress = address;
                }
            }
        }

        return candidateAddress != null ? candidateAddress.getHostAddress() : getLocalHostAddress();
    }

    /**
     * 获取所有网卡的IP地址，排除回环地址、虚拟地址等。
     *
     * @return 所有网卡的IP地址数组
     * @throws SocketException 如果发生I/O错误
     */
    public static String[] getLocalIps() throws SocketException {
        List<String> list = new ArrayList<>();

        // 获取并遍历网卡设备
        Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
        while (enumeration.hasMoreElements()) {
            NetworkInterface netInter = enumeration.nextElement();
            if (!netInter.isUp() || netInter.isLoopback() || netInter.isVirtual() || netInter.isPointToPoint()) {
                continue;
            }
            Enumeration<InetAddress> addresses = netInter.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                if (addr.isLoopbackAddress() || !addr.isSiteLocalAddress() || addr.isAnyLocalAddress()) {
                    continue;
                }
                list.add(addr.getHostAddress());
            }
        }
        return list.toArray(new String[0]);
    }

    /**
     * 获取机器的MAC地址。
     *
     * @return 机器的MAC地址
     * @throws SocketException      如果发生I/O错误
     * @throws UnknownHostException 如果无法解析本地主机名称为地址
     */
    public static String getMacAddress() throws UnknownHostException, SocketException {
        InetAddress ia = InetAddress.getLocalHost();
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                builder.append("-");
            }
            int temp = mac[i] & 0xff;
            String str = Integer.toHexString(temp);
            if (str.length() == 1) {
                builder.append("0").append(str);
            } else {
                builder.append(str);
            }
        }

        return builder.toString().toUpperCase();
    }
}
