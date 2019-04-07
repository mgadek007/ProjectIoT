package marcing.iotproject.inConnectionServlet.control;

import com.google.gson.*;
import marcing.iotproject.basicElements.DataBlock;
import marcing.iotproject.errors.ConvertErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;

public class BodyReader {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String PROBLEM_WITH_CONVERT_INPUT_DATA = "Problem with convert data";

    public DataBlock convertJsonToDataBlock(HttpServletRequest request) throws IOException {
        DataBlock dataBlock;
        try{
            JsonElement elem = new JsonParser().parse(new InputStreamReader(request.getInputStream()));
            Gson gson  = new GsonBuilder().create();
            dataBlock = gson.fromJson(elem, DataBlock.class);
        } catch (JsonIOException | JsonSyntaxException e){
            log.error(PROBLEM_WITH_CONVERT_INPUT_DATA);
            log.error(e.toString());
            throw new ConvertErrorException(PROBLEM_WITH_CONVERT_INPUT_DATA);
        }

        return dataBlock;
    }
}
