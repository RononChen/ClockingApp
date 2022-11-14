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


         query = "INSERT INTO pointage(matricule_ref,id_jour_ref,heure_entree) " +
                "VALUES(?,?,TIME(?,?))";


        statement = Database.compileStatement(query);
        statement.bindLong(1, employee.getRegistrationNumber());
        statement.bindLong(2,id);
        statement.bindString(3, "NOW");
        statement.bindString(4,"LOCALTIME");
        statement.executeInsert();
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


        query="UPDATE employee SET status=? WHERE matricule=?";
        statement=Database.compileStatement(query);
        statement.bindString(1,employee.getCurrentStatus());
        statement.bindLong(2,employee.getRegistrationNumber());
        statement.executeUpdateDelete();


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

    public boolean hasNotClockedIn(Employee employee,Day day) {
        int id=day.getId();
        int n;
        //open();
        String query = "SELECT * FROM pointage WHERE matricule_ref=? AND id_jour_ref=?";
        String[] selectArgs = {
                String.valueOf(employee.getRegistrationNumber()),String.valueOf(id)
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