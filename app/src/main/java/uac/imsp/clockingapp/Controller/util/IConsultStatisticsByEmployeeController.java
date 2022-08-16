package uac.imsp.clockingapp.Controller.util;

import java.text.ParseException;

public interface IConsultStatisticsByEmployeeController {

    void onConsultStatisticsForEmployee(int number);
    void onMonthSelected(int month) throws ParseException;

}
