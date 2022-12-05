package uac.imsp.clockingapp.Controller.control;

import android.content.Context;

import uac.imsp.clockingapp.Controller.util.IClockInOutController;
import uac.imsp.clockingapp.Models.dao.ClockingManager;
import uac.imsp.clockingapp.Models.entity.Day;
import uac.imsp.clockingapp.Models.dao.DayManager;
import uac.imsp.clockingapp.Models.entity.Employee;
import uac.imsp.clockingapp.Models.dao.EmployeeManager;
import uac.imsp.clockingapp.View.util.IClockInOutView;

public class ClockingInOutController
        implements IClockInOutController {

private final IClockInOutView clockInOutView;
private final Context context;
    public ClockingInOutController(IClockInOutView clockInOutView) {

        this.clockInOutView=clockInOutView;
        clockInOutView.onLoad();
        this.context= (Context) this.clockInOutView;

    }

    public ClockingInOutController(IClockInOutView clockInOutView,Context context) {
        this.clockInOutView=clockInOutView;
        clockInOutView.onLoad();
            this.context=context;
    }

    public void clock(int number,String date,String time){
        //Take  time into account later
        Employee employee;
        Day day;
        EmployeeManager employeeManager;
        ClockingManager clockingManager;
        DayManager dayManager;

        employee = new Employee(number);
        //connection to  database , employee table
        employeeManager = new EmployeeManager(context);
        //open employee connection
        employeeManager.open();
        day=new Day(date);
        dayManager=new DayManager(context);
        dayManager.open();
        //A day is created and has an id
        dayManager.create(day);


        if (!employeeManager.exists(employee))
            //employee does not exist
            clockInOutView.onClockingError(1);

        else //The employee exists
        {

            //check if the employee shouldWorkToday or not
            //case not
            if (employeeManager.shouldNotWorkToday(employee))
                clockInOutView.onClockingError(2);
            else {

                //Connection to clocking table
                clockingManager = new ClockingManager((Context) clockInOutView);
                //open  clocking connection
                clockingManager.open();
                if (clockingManager.hasNotClockedIn(employee, day)) {


                    clockingManager.clockIn(employee, day.getDate(),time);

                    clockInOutView.onClockInSuccessful();
                } else if (clockingManager.hasNotClockedOut(employee, day)) {
                    clockingManager.clockOut(employee, day);
                    clockInOutView.onClockInSuccessful();
                } else {
                    clockInOutView.onClockingError(3);
                }
                //close  clocking connection
                clockingManager.close();

            }
        }


    }

        @Override
    public void onClocking(int number) {
        //Take  time into account later
            Employee employee;
            Day day;
            EmployeeManager employeeManager;
            ClockingManager clockingManager;
            DayManager dayManager;

            employee = new Employee(number);
            //connection to  database , employee table
            employeeManager = new EmployeeManager(context);
            //open employee connection
            employeeManager.open();
            day=new Day();
            dayManager=new DayManager(context);
            dayManager.open();
            //A day is created and has an id
            dayManager.create(day);


            if (!employeeManager.exists(employee))
                //employee does not exist
                clockInOutView.onClockingError(1);

            else //The employee exists
            {

                //check if the employee shouldWorkToday or not
                //case not
                if (employeeManager.shouldNotWorkToday(employee))
                    clockInOutView.onClockingError(2);
                else {

                    //Connection to clocking table
                    clockingManager = new ClockingManager((Context) clockInOutView);
                    //open  clocking connection
                    clockingManager.open();
                    if (clockingManager.hasNotClockedIn(employee, day)) {


                        clockingManager.clockIn(employee, day);

                        clockInOutView.onClockInSuccessful();
                    } else if (clockingManager.hasNotClockedOut(employee, day)) {
                        clockingManager.clockOut(employee, day);
                        clockInOutView.onClockOutSuccessful();
                    } else {
                        clockInOutView.onClockingError(3);
                    }
                    //close  clocking connection
                    clockingManager.close();

                }
            }
            //close employee management connection
            //employeeManager.close();

        }

}
