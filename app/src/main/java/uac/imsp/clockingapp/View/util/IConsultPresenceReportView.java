package uac.imsp.clockingapp.View.util;

public interface IConsultPresenceReportView {
    // onStart, the view asks the user to select the month
    void onStart(String message);


    void onMonthSelected(Character[] report, int fitstDayNumber);



}


