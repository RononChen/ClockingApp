package uac.imsp.clockingapp.Controller;

public interface IConsultStatisticsController {
    String formatDate(int year,int month,int day );
    void onConsultStatisticsByService();
    //We'll get the name of the selected service

    void onServiceSelected(String serviceName);
    void onStartDateSelected(int year,int month,int day);
    void onEndDateSelected(int year,int month,int day);

    //Quand l'employé est sélectiooné on recupère son matricule
    void OnEmployeeSelected(int matricule);

   // void onConsultStatisticsByEmployee();

}

