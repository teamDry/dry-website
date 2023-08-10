package org.dry.util;

import org.dry.entity.Admin;
import org.dry.entity.Member;

/**
 * Test에 도움이 되는 Util 메서드 작성
 * 작성시 static한 메서드로 작성해 사용이 편하게함
 * ex) TestUtil.getDummyData
 */

public class TestUtil {
    public static Member getDummyMember() {
    /**
     * testId, testPassword, testNick, test@naver.com
     */
        return Member.of("testId", "testPassword", "testNick", "test@naver.com");
    }

    /**
     * testAdmin, testAdminPw, testAdminNick, admin@naver.com
     */
    public static Admin getDummyAdmin() {
        return Admin.of("testAdmin", "testAdminPw", "testAdminNick", "admin@naver.com");
    }
}
