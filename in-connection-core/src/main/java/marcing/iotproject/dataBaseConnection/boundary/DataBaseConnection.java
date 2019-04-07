package marcing.iotproject.dataBaseConnection.boundary;

import marcing.iotproject.DataBlock;
import marcing.iotproject.dataBaseConnection.control.DatabaseConverter;

import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;

public class DataBaseConnection extends HttpServlet {

    private final DatabaseConverter databaseConverter = new DatabaseConverter();

    public void loadDataToDataBase(DataBlock dataBlock) {
        String message = databaseConverter.prepareMessageForDataBase(dataBlock);


    }

    public List<DataBlock> getListDataBlockFromDataBase (String roomId){
        return new ArrayList<>();
    }
}
