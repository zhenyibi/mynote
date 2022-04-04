package com.mynote.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhenyiBi
 * @date 2022/4/4
 */

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置首页动态包含的页面
        request.setAttribute("changePage", "note/list.jsp");

        // 请求转发到 index.jsp
        request.getRequestDispatcher("index.jsp").forward(request, response);

    }
}
