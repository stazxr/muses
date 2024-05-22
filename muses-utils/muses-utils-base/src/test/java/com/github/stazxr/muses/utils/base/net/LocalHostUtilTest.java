package com.github.stazxr.muses.utils.base.net;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

@Ignore
public class LocalHostUtilTest {
    @Test
    public void testAllMethod() throws Exception {
        System.out.println(LocalHostUtil.getLocalHostName());
        System.out.println(LocalHostUtil.getLocalHostAddress());
        System.out.println(LocalHostUtil.getLocalIp());
        System.out.println(Arrays.toString(LocalHostUtil.getLocalIps()));
        System.out.println(LocalHostUtil.getMacAddress());
    }
}
