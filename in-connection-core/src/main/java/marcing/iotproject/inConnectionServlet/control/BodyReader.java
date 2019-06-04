package marcing.iotproject.inConnectionServlet.control;

import com.google.gson.*;
import marcing.iotproject.basicElements.DataBlock;
import marcing.iotproject.errors.ConvertErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;

public class BodyReader {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String PROBLEM_WITH_CONVERT_INPUT_DATA = "Problem with convert data";

    public DataBlock convertJsonToDataBlock(InputStream request) {
        DataBlock dataBlock;
        try{
            JsonElement elem = new JsonParser().parse(new InputStreamReader(request));
            Gson gson  = new GsonBuilder().create();
            dataBlock = gson.fromJson(elem, DataBlock.class);
        } catch (JsonIOException | JsonSyntaxException e){
            log.error(PROBLEM_WITH_CONVERT_INPUT_DATA);
            log.error(e.toString());
            throw new ConvertErrorException(PROBLEM_WITH_CONVERT_INPUT_DATA);
        }

        return dataBlock;
    }

    public DataBlock convertJsonStringToDataBlock(String request) {
        DataBlock dataBlock;
        try{
            GsonBuilder gson  = new GsonBuilder();
            gson.serializeNulls();
            Gson gson1 = gson.create();
            dataBlock = gson1.fromJson(request, DataBlock.class);
        } catch (JsonIOException | JsonSyntaxException e){
            log.error(PROBLEM_WITH_CONVERT_INPUT_DATA);
            log.error(e.toString());
            throw new ConvertErrorException(PROBLEM_WITH_CONVERT_INPUT_DATA);
        }
        return dataBlock;
    }
}
