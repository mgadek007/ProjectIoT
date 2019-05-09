package marcing.iotproject.dataPreparator.boundary;

import marcing.iotproject.dataPreparator.control.DatePreparator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DataPreparatorServlet extends HttpServlet {

    private static final String SUCCESS_RESULT = "Success prepared data";
    private static final String FAILED_RESULT = "Something go wrong";
    private static final int BEGINING_OF_ID = 1;

    private DatePreparator datePreparator = new DatePreparator();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getPathInfo().substring(BEGINING_OF_ID);
        try {
            datePreparator.prepareData(id);
            response.setStatus(200);
            response.getOutputStream().println(SUCCESS_RESULT);
        }catch (Exception e){
            response.setStatus(409);
            response.getOutputStream().print(FAILED_RESULT);
        }

    }

}
