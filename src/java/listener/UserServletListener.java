/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/ServletListener.java to edit this template
 */
package listener;

import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import models.Accounts;

/**
 * Web application lifecycle listener.
 *
 * @author ASUS
 */
public class UserServletListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        int nSai = 0;
        se.getSession().setAttribute("sls", nSai);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext ud = session.getServletContext();
        Accounts x = (Accounts) session.getAttribute("user");
        System.out.println("axxxxxxass"+x);
        Map<String, HttpSession> danhsach = (Map<String, HttpSession>)se.getSession().getServletContext().getAttribute("online");

        danhsach.remove(x.getAccount());
        ud.setAttribute("online", danhsach);
    }
}
