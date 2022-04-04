package com.mynote;

import cn.hutool.crypto.digest.DigestUtil;
import com.mynote.dao.BaseDao;
import com.mynote.dao.UserDao;
import com.mynote.po.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println("admin加密的密码为：" + DigestUtil.md5Hex("123456"));
    }

    @Test
    public void testAdd() {
        String sql = "insert into tb_user (uname, upwd, nick, head, mood) values(?, ?, ?, ?, ?)";
        List<Object> params = new ArrayList<>();
        params.add("bzy");
        params.add("e10adc3949ba59abbe56e057f20f883e");
        params.add("bb");
        params.add("404.jpg");
        params.add("hello,bzy!");
        int row = BaseDao.executeUpdate(sql, params);
        System.out.println(row);
    }
}
