package com.mynote.web;

import com.mynote.po.User;
import com.mynote.service.UserService;
import com.mynote.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhenyiBi
 * @date 2022/3/27
 */

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收用户行为
        String actionName = request.getParameter("actionName");
        //判断用户行为，调用对应方法
        if ("login".equals(actionName)) {
            //用户登录
            userLogin(request, response);
        }
    }

    /**
     * 用户登录
         1.获取参数  (姓名，密码)
         2.调用Service层的方法，返回ResultInfo对象
         3.判断是否登录成功
             如果失败
                 将ResultInfo对象设置request作用域中
                 请求转发跳转到登录页面
             如果成功
                 将用户信息存到session作用域
                 判断用户是否选择记住密码(rem的值是1)
                     如果是，将用户姓名和密码存到cookie中，设置失效时间，并响应给客户端
                     如果否，清空原有的cookie对象
                 重定向跳转到index页面（云日记首页）
     * @param request 请求
     * @param response 响应
     */
    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取参数  (姓名，密码)
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");

        //2.调用Service层的方法，返回ResultInfo对象
        ResultInfo<User> resultInfo = userService.userLogin(userName, userPwd);

        //3.判断是否登录成功
        if (resultInfo.getCode() == 2000) { //如果成功
            //将用户信息存到session作用域
            request.getSession().setAttribute("user",resultInfo.getResult());
            //判断用户是否选择记住密码(rem的值是1)
            String rem = request.getParameter("rem");
            if ("1".equals(rem)) {
                //如果是，将用户姓名和密码存到cookie中，设置失效时间，并响应给客户端
                Cookie cookie = new Cookie("user", userName+'-'+userPwd);
                cookie.setMaxAge(3*24*60*60);
                response.addCookie(cookie);
            } else {
                //如果否，清空原有的cookie对象
                Cookie cookie = new Cookie("user",null);
                //删除cookie,设置时间为0
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            //重定向跳转到index页面（云日记首页）
            response.sendRedirect("index.jsp");

        } else { //失败
            //将ResultInfo对象设置request作用域中
            request.setAttribute("resultInfo",resultInfo);
            //请求转发跳转到登录页面
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
    }
}
