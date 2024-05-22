package com.github.stazxr.muses.utils.base.seq;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class UuidUtilTest {
    @Test
    public void testAllMethod() {
        System.out.println("UUID 版本号：" + UuidUtil.genUuidVer());
        System.out.println("UUID 字符串：" + UuidUtil.genUuidStr());
        System.out.println("UUID 8位字符串：" + UuidUtil.gen8BitUuidStr());
        System.out.println("UUID 16位字符串：" + UuidUtil.gen16BitUuidStr());
    }
}
