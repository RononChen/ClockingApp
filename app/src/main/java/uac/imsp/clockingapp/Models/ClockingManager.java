package uac.imsp.clockingapp.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

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


    //create,delete,update

    //for clocking in

    public void clockIn(Employee employee, Day day) {
        //open()
        int id;
        SQLiteStatement statement;
        id=day.getId();



        String query = " INSERT INTO pointage(matricule_ref,id_jour_ref,heure_entree) VALUES(?,?,?)";

        statement = Database.compileStatement(query);
        statement.bindLong(1, employee.getRegistrationNumber());
        statement.bindLong(2,id);
        statement.bindString(3, "TIME('NOW','LOCALTIME'");

        statement.executeInsert();
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