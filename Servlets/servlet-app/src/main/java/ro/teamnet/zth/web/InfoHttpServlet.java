package ro.teamnet.zth.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 7/12/2016.
 */
public class InfoHttpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map <String, String> mymap = new HashMap<String, String>();
        Enumeration headerName  = req.getHeaderNames();

        while(headerName != null) {
            String key = (String) headerName.nextElement();
            String value = req.getHeader(key);
            mymap.put(key, value);
        }

        StringBuilder mytable = new StringBuilder();
        mytable.append("<table><tr>");

        for(String str : mymap.keySet()) {
            mytable.append("<th>" + str  + "</th>");
        }
        mytable.append("</tr>");
        for(String str : mymap.keySet()) {
            mytable.append("<td>" + mymap.get(str)  + "</td>");
        }
        mytable.append("</table>");

    }
}
