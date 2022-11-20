//import jakarta.servlet.*;
import java.io.*;
//import jakarta.servlet.http.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.*;
//import java.util.Enumeration;
//import java.sql.*;

// To directly compile (NOT test) this servlet, open a Git bash terminal and type in
// One of these, preferably the second.
// javac -classpath 'lib/servlet-api.jar' FrontEnd/Servlets/homePageServlet.java
// javac -classpath 'lib/servlet-api.jar' -d build FrontEnd/Servlets/homePageServlet.java

@WebServlet(name = "Log Out", urlPatterns = "/logout")
public class logout extends HttpServlet {

    Logger svltLog;
    boolean loggedIn = true;
    String pageToDisplay;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException 
    {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        if (svltLog == null) {
            svltLog  = Logger.getLogger(this.getClass().getName() + "Servlet");
        }
        svltLog.warning("Firing up servlet " + this.getClass().getName());

        HttpSession session = request.getSession(true);
        // These are, hopefully, going to be tested against the database at some point.
        //PrintWriter out = response.getWriter();

        session.invalidate();
        request.getRequestDispatcher("index.html").forward(request, response); // Redisplay login.html.
    } 
}