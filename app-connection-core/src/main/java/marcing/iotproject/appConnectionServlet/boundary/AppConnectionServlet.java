package marcing.iotproject.appConnectionServlet.boundary;

import marcing.iotproject.appConnectionServlet.entity.DataBlock;
import marcing.iotproject.dataBaseConnectionForApp.boundary.DataBaseConnectionForApp;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AppConnectionServlet extends HttpServlet {

    private DataBaseConnectionForApp dataBaseConnectionForApp = new DataBaseConnectionForApp();

    private static final int BEGINING_OF_ID = 1;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getPathInfo().substring(BEGINING_OF_ID);
        DataBlock result = dataBaseConnectionForApp.getDataFromBase(id);
        response.setStatus(200);
        response.getOutputStream().println("App Connection Working :D "+id);
    }

}
