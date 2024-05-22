package com.github.stazxr.muses.utils.base.net;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

@Ignore
public class IpUtilTest {
    @Test
    public void testAllMethod() throws Exception {
        long ipNum = IpUtil.formatIpToNum("192.123.27.2");
        System.out.println(ipNum);
        String ip = IpUtil.parseNumToIp(ipNum);
        System.out.println(ip);

        String charFromIpString = IpUtil.get7CharFromIpString("192.255.27.1");
        System.out.println(charFromIpString);

        System.out.println("-----------------");

        int count = 1;
        long ipNum1 = IpUtil.formatIpToNum("192.255.27.3");
        ipNum1 |= (long) count << 32;
        System.out.println("ipNum1: " + ipNum1);

        String parseStr = Long.toString(ipNum, 36);
        StringBuilder builder = new StringBuilder("0000000");
        builder.replace(7 - parseStr.length(), 7, parseStr);
        String char7 = builder.toString().toUpperCase();
        System.out.println("char7: " + char7);

        long ipNum2 = Long.parseLong(parseStr, 36);
        System.out.println("ipNum2: " + ipNum2);
    }
}
