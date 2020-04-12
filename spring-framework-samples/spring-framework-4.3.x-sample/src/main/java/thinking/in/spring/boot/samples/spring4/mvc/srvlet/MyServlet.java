package thinking.in.spring.boot.samples.spring4.mvc.srvlet;

import org.springframework.web.servlet.FrameworkServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyServlet
        //extends FrameworkServlet
    extends HttpServlet
{


    private void handler(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("hendle");
        ServletContext servletContext = getServletContext();
        RequestDispatcher rd = null;
        rd = servletContext.getRequestDispatcher("/userList.jsp");
        try {
            rd.forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        handler(request, response);
    }
}
