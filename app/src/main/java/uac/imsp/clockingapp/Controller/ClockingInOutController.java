package uac.imsp.clockingapp.Controller;

import android.content.Context;

import uac.imsp.clockingapp.Models.ClockingManager;
import uac.imsp.clockingapp.Models.Employee;
import uac.imsp.clockingapp.Models.EmployeeManager;
import uac.imsp.clockingapp.View.IClockInOutView;

public class ClockingInOutController
        implements IClockInOutController {

private  IClockInOutView clockInOutView;
    public ClockingInOutController(IClockInOutView clockInOutView) {

        this.clockInOutView=clockInOutView;
        clockInOutView.onLoad( "Scannez votre code QR pour pointer");



    }

        @Override
    public void onClocking(int number) {
        //Take into time account later
            Employee employee;
            EmployeeManager employeeManager;
            ClockingManager clockingManager;

            employee = new Employee(number);
            //connection to  database , employee table
            employeeManager = new EmployeeManager((Context) clockInOutView);
            //open employee connection
            employeeManager.open();
            if (!employeeManager.exists(employee))
                //employee does not exist
                clockInOutView.onClockingError("Employé non retrouvé");

            else
            //The employee exists
            {
                //Connection to clocking table
                clockingManager = new ClockingManager((Context) clockInOutView);
                //open  clocking connection
                clockingManager.open();
             if (clockingManager.hasNotClockedIn(employee)) {

                    clockingManager.clockIn(employee);
                    clockInOutView.onClockingSuccessful("Entrée marquée avec succès");
                }
             else if (clockingManager.hasNotClockedOut(employee)) {
                    clockingManager.clockOut(employee);
                    clockInOutView.onClockingSuccessful("Sortie marquée avec succès");
                }

             else {
                   clockInOutView.onClockingError("Vous avez déjà marqué votre entrée-sortie de la journée");
                }
                //close  clocking connection
                clockingManager.close();

            }
            //close employee management connection
            employeeManager.close();

        }

}
