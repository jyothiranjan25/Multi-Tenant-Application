package com.example.jkpvt;

import java.io.*;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<h2>" + request.getSession().getAttribute("username") + "</h2>");
        out.println("<h2>" + request.getSession().getAttribute("sessionId") + "</h2>");
        out.println("<a href=/JKPVT_war_exploded/logout> Logout </a>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}