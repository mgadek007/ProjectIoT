package marcing.iotproject.dataBaseConnection.control;


import marcing.iotproject.dataBaseConnection.entity.QueryPreparator;
import marcing.iotproject.errors.ConvertError;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class DataBaseConvert {

    private static final String PROBLEM_WITH_CONVERT = " Problem with data From DB";
    private static final String EMPTY_STRING = null;
    private static final String ID_ROOM = "IdRoom";

    private QueryPreparator queryPreparator = new QueryPreparator();

    List<String> convertToList(ResultSet dbResult, String attrName) {
        List<String> result = new ArrayList<>();
        try {
            while (dbResult.next()){
                result.add(dbResult.getString(attrName));
            }
        }catch (SQLException e){
            throw new ConvertError(PROBLEM_WITH_CONVERT);
        }
        return result;
    }

    String getAttributeFromResult(ResultSet dbResult, String attrName) {
        String result = EMPTY_STRING;
        try {
            while (dbResult.next()){
                result = dbResult.getString(attrName);
            }

        }catch (SQLException e){
            throw new ConvertError(PROBLEM_WITH_CONVERT);
        }
        return result;
    }

    ArrayList getList(ResultSet result) {
        ArrayList list = new ArrayList();
        try {
            while (result.next()){
                list.add(result.getString(ID_ROOM));
            }
        }catch (SQLException e){
            throw new ConvertError(PROBLEM_WITH_CONVERT);

        }
        return list;
    }

}
