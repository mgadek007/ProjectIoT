package marcing.iotproject.inConnectionServlet.boundary;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InConnectionServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.getOutputStream().print("Chuj! dobrze! dziala xDD");

    }

}
