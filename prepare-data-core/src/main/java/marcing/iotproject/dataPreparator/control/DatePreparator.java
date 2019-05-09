package marcing.iotproject.dataPreparator.control;


import marcing.iotproject.dataBaseConnection.boundary.DataBaseConnectionDB;
import marcing.iotproject.dataPreparator.entity.DataBlock;

public class DatePreparator {

    private DataBlockPreparator dataBlockPreparator = new DataBlockPreparator();
    private DataBaseConnectionDB dataBaseConnectionDB = new DataBaseConnectionDB();


    public void prepareData(String id) {
        DataBlock data = dataBlockPreparator.prepareDataBlockForFinalTable(id);
        dataBaseConnectionDB.loadToFinalDB(data);
    }


}
