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

        String charFromIpString = IpUtil.get7CharFromIpString("192.255.27.2", 3);
        System.out.println(charFromIpString);
        System.out.println(Arrays.toString(IpUtil.parse7CharToIpCountAry(charFromIpString)));
    }
}
