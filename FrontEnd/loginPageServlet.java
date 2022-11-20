import java.io.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.*;

@WebServlet(name = "Login Page Servlet", urlPatterns = "/loginServlet")
public class loginPageServlet extends HttpServlet {
    static final long serialVersionUID = 20200423L;
    Logger svltLog;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException 
    {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // utilize the svltLog
        if (svltLog == null) {
            svltLog  = Logger.getLogger (this.getClass().getName() + "Servlet");
        }
        svltLog.warning ("Firing up servlet " + this.getClass().getName());
        
        HttpSession session = request.getSession(true);
        //retrieve the username and password submitted by the form
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("password");

        svltLog.info("initializing the createAccount class");
        user_login loginAccount = new user_login(userName,passWord);
        svltLog.info("class created successfully");
        svltLog.info("running class function");
        Boolean login = loginAccount.sendInfo();
        svltLog.info("successfully run the class function");
        
        svltLog.warning("connection was tested and now servlet will act according to login true or false");
        if (login) {
            svltLog.info("user is logged in");
            session.setAttribute("username", userName); // set attribute of username so that it can be retrieved in other pages
            response.sendRedirect("/Banking/homePageServlet"); // Redirect to home page
        } else {
            svltLog.info("user was not logged in");
            session = request.getSession(true);
            // These are, hopefully, going to be tested against the database at some point.
            // Redisplay the Login page with error messages
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");

            out.println("<html lang=\"en\">");
            out.println("   <head>");
            out.println("       <meta charset=\"utf=8\">");
            out.println("       <link rel=\"stylesheet\" href=\"global.css\">");
            out.println("       <link rel=\"stylesheet\" href=\"assets/style/font-awesome-4.7.0/css/font-awesome.min.css\">");
            out.println("       <title>Login - Atlantic Bank</title>");
            out.println("   </head>");
            out.println("");
            out.println("   <body>");
            out.println("       <nav class=\"navbar\"");
            out.println("           <a href=\"index.html\"><i class=\"fa fa-home\"></i></a>");
            out.println("           <li><a href=\"credit_score_checker.html\">Credit Score Checker</a></li>");
            out.println("           <li>");
            out.println("               <div class=\"dropdown\" style=\"float:left;\">");
            out.println("                   <button class=\"dropbtn\">MORE INFORMATION</button>");
            out.println("                   <div class=\"dropdown-content\"style=\"left:0;\">");
            out.println("                       <a href=\"faq.html\">FAQS</a> ");
            out.println("                       <a href=\"about.html\">ABOUT US</a> ");
            out.println("                       <a href=\"contact.html\">CONTACT US</a> ");
            out.println("                   </div>");
            out.println("               </div>");
            out.println("          </li>");
            out.println("          <li><a href=\"login.html\" class=\"outline\">Log In</a></li>");
            out.println("          <li><a href=\"create_account.html\" class=\"outline\">Register</a></li>");
            out.println("       </nav>");
            out.println("");
            out.println("          <h1>Log in to Atlantic Bank</h1>");
            out.println("       <form action=\"/Banking/loginServlet\" method=\"post\">");
            out.println("           User name: <input type=\"text\" name=\"userName\" size=\"20\"><br>");
            out.println("              Password: <input type=\"password\" name=\"password\" size=\"20\"><br><br>");
            out.println("           <input type=\"submit\" value=\"submit\">");
            out.println("       </form>");
            out.println("          <p>Username or Password was wrong</p>");
            out.println("       <footer>");
            out.println("           <p> For any questions, please click the email below.<br>");
            out.println("           <a href=\"mailto:aljames@gmail.com\">aljames@gmail.com</a></p>");
            out.println("       </footer>");
            out.println("   </body>");
            out.println("</html>");
        }
    }
}
