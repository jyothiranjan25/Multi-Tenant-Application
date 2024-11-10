package com.example.jkpvt;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

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