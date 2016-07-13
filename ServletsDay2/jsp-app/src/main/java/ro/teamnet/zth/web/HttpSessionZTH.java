package ro.teamnet.zth.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by user on 7/13/2016.
 */
public class HttpSessionZTH extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("username");
        String pass = req.getParameter("password");
        HttpSession mysession = req.getSession();

        if(user.equals("admin") && pass.equals("admin"))
            resp.getWriter().write("Welcome back" + user);
        else{
            mysession.setAttribute("user", user);
            mysession.setAttribute("session", mysession);
            resp.sendRedirect("views/loginFail.jsp");

        }

    }
}
