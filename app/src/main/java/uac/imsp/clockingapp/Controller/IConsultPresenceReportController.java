package uac.imsp.clockingapp.Controller;


import java.util.Hashtable;

public interface IConsultPresenceReportController {
    void onConsultPresenceReport(int number);
     Hashtable<String,Character> onMonthSelected(int month);

}
