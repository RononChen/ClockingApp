package uac.imsp.clockingapp.Models.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import uac.imsp.clockingapp.Models.dbAdapter.ClockingSQLite;
import uac.imsp.clockingapp.Models.entity.Day;
import uac.imsp.clockingapp.Models.entity.Employee;

public class ClockingManager {

    private SQLiteDatabase Database = null;

    private final ClockingSQLite clockingSQLite;

    public ClockingManager(Context context) {

        clockingSQLite = new ClockingSQLite(context);
    }


    public SQLiteDatabase    open() {
        if (Database == null)
            Database = clockingSQLite.getWritableDatabase();
        return Database;
    }

    public void close() {
        if (Database != null && Database.isOpen())
            Database.close();
    }
/**This method allows us to clock the given employee in it requires the employee wotks the given day**/
    public void clockIn(Employee employee, Day day) {
        Cursor cursor;
        String attendedEntryTime;
        String currentEntryTime;
        String [] selectArgs;
        int id;
        SQLiteStatement statement;
        String query;
        id=day.getId();

         query="SELECT TIME('NOW','LOCALTIME')"; //get the current time
        cursor=Database.rawQuery(query,null);
        currentEntryTime=cursor.getString(0);
        cursor.close();


         query = "UPDATE  pointage SET id_jour_ref=? AND heure_entree=?" +
                 " WHERE matricule_ref=? AND date_jour=TIME(?,?) ";


        statement = Database.compileStatement(query);
        statement.bindLong(1,id);
        statement.bindString(2,"NOW");
        statement.bindLong(3, employee.getRegistrationNumber());
        statement.bindString(4, "NOW");
        statement.bindString(5,"LOCALTIME");
        statement.executeUpdateDelete();
        //update the current state of the employee
       //we wanna get the attended entry time of the employee
        query="SELECT heure_debut_officielle FROM planning " +
                "JOIN employe ON id_planning=id_planning_ref" +
                " WHERE matricule=?";
        selectArgs= new String[]{String.valueOf(employee.getRegistrationNumber())};
        cursor=Database.rawQuery(query,selectArgs);
        //if(cursor.moveToFirst())
            attendedEntryTime=cursor.getString(0);
        if(currentEntryTime.compareTo(attendedEntryTime)<=0)

            employee.setCurrentStatus("Présent");
        else
            employee.setCurrentStatus("Retard");


        cursor.close();
        updateDailyAttendance(employee,employee.getCurrentStatus());


        query="UPDATE employee SET status=? WHERE matricule=?";
        statement=Database.compileStatement(query);
        statement.bindString(1,employee.getCurrentStatus());
        statement.bindLong(2,employee.getRegistrationNumber());
        statement.executeUpdateDelete();


        statement.executeUpdateDelete();

    }

    public void updateDailyAttendance(Employee employee,String status){
        String query="UPDATE pointage SET statut=?" +
                " WHERE matricule_ref=? AND date_jour=DATE(?,?)";
        SQLiteStatement statement=Database.compileStatement(query);
        statement.bindString(1,status);
        statement.bindLong(2,employee.getRegistrationNumber());
        statement.bindString(3,"NOW");
        statement.bindString(3,"LOCALTIME");
        statement.executeUpdateDelete();

    }

    //for clocking out
    public void clockOut(Employee employee, Day day) {
        int id;
        id=day.getId();


        SQLiteStatement statement;
        String query = "UPDATE pointage set heure_sortie=? WHERE matricule_ref=? AND date_jour_ref=?";
        statement = Database.compileStatement(query);
        statement.bindString(1, "TIME('NOW','LOCALTIME')");
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.bindLong(3, id);
        statement.executeUpdateDelete();
     employee.setCurrentStatus("Sortie");
        query="UPDATE employee SET status=? WHERE matricule=?";
        statement=Database.compileStatement(query);
        statement.bindString(1,employee.getCurrentStatus());
        statement.bindLong(2,employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }
/**This method check if the given employee has(already) clocked in the given day**/
    public boolean hasNotClockedIn(Employee employee,Day day) {
        int id=day.getId();
        int n;
        //open();
        String query = "SELECT * FROM pointage WHERE matricule_ref=? AND id_jour_ref=?" +
                " AND statut!=? AND statut!=?";
        String[] selectArgs = {
                String.valueOf(employee.getRegistrationNumber()),String.valueOf(id),
                "Présent",
                "Retard"

        };
        Cursor cursor = Database.rawQuery(query, selectArgs);

          n=cursor.getCount();
          cursor.close();
        return n == 0;
    }

    public boolean hasNotClockedOut(Employee employee,Day day) {

       // open();
        String out;
int id=day.getId(),n;
        String query = "SELECT heure_sortie FROM pointage WHERE matricule_ref=? AND id_jour_ref=?";
        String[] selectArgs = {
                String.valueOf(employee.getRegistrationNumber()),String.valueOf(id)
        };
        Cursor cursor = Database.rawQuery(query, selectArgs);
        cursor.moveToFirst();
        n=cursor.getCount();
        out=cursor.getString(3);
        cursor.close();
        return n == 0 || out.equals("");
    }


}