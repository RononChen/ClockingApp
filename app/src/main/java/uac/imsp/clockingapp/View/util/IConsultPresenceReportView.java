package uac.imsp.clockingapp.View.util;

public interface IConsultPresenceReportView {
    // onStart, the view asks the user to select the month
    void onStart(String date);


    void onMonthSelected(String[] report, int fitstDayNumber);
    void onReportError(boolean nextError);



}


