package marcing.iotproject.managementRoom.control;

import marcing.iotproject.dataPreparator.entity.DataBlock;
import marcing.iotproject.managementRoom.entity.Converter;
import marcing.iotproject.managementRoom.entity.ManageDictionary;
import marcing.iotproject.managementRoom.entity.ManagementDTO;

public class AirConditionChecker {

    private Converter converter = new Converter();
    private static final String TRUE = ManageDictionary.TRUE;
    private static final String FALSE = ManageDictionary.FALSE;

    private static final double NORM_AIR_OUT_CON = 0.4;
    private static final double MIN_TEMP_OUT = 13.0;

    public ManagementDTO checkAir(DataBlock dataBlock, ManagementDTO managementDTO){
        boolean isSound = converter.checkSound(dataBlock.isSoundDetected());
        boolean isPeopleInsight = converter.isPeopleInsight(dataBlock.getPeopleInside());
        if (isSound && isPeopleInsight)
        {
            double airOut = converter.convertToDouble(dataBlock.getAirQuaOut());
            double airIn = converter.convertToDouble(dataBlock.getAirQuaIn());
            double tempOut = converter.convertToDouble(dataBlock.getTempOut());
            if (airOut < NORM_AIR_OUT_CON || tempOut < MIN_TEMP_OUT){
                managementDTO.setCloseWindow(TRUE);
                managementDTO.setTurnOnAirCon(TRUE);
                managementDTO.setSetTempOnAirCon(dataBlock.getTempIn());
            }

        }else {
            managementDTO.setCloseWindow(TRUE);
            managementDTO.setTurnOnAirCon(FALSE);
        }
        return managementDTO;
    }
}
