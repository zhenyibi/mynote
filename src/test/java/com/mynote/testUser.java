package com.mynote;

import cn.hutool.crypto.digest.DigestUtil;
import com.mynote.dao.UserDao;
import com.mynote.po.User;
import org.junit.Test;

/**
 * @author zhenyiBi
 * @date 2022/3/27
 */
public class testUser {

    @Test
    public void testQueryUserByName() {
        UserDao userDao = new UserDao();
        User user = userDao.queryUserByName("admin");
        System.out.println("admin的密码为：" + user.getUpwd());
        System.out.println("admin加密的密码为：" + DigestUtil.md5Hex(user.getUpwd()));
    }
}
