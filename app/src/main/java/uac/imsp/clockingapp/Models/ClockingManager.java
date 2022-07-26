package uac.imsp.clockingapp.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class ClockingManager {

    private SQLiteDatabase Database = null;

    private ClockingSQLite clockingSQLite;

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

    public boolean clockIn(Employee employee) {
        open();
        SQLiteStatement statement;


        String query = " INSERT INTO pointage(matricule_ref,date_jour_ref,heure_entree) VALUES(?,?,?)";
        statement = Database.compileStatement(query);
        statement.bindLong(1, employee.getRegistrationNumber());
        statement.bindString(2, "DATE('NOW','LOCALTIME'");
        statement.bindString(3, "TIME('NOW','LOCALTIME'");

        statement.executeInsert();
        return false;
    }


    //for clocking out
    public boolean clockOut(Employee employee) {
        open();
        SQLiteStatement statement;
        String query = "UPDATE pointage set heure_sortie=? WHERE matricule_ref=? AND date_jour_ref=?";
        statement = Database.compileStatement(query);
        statement.bindString(1, "TIME('NOW','LOCALTIME')");
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.bindString(3, "DATE('NOW','LOCALTIME')");
        statement.executeUpdateDelete();
        return false;
    }

    public boolean hasNotClockedIn(Employee employee) {
        open();
        String query = "SELECT * FROM pointage WHERE matricule_ref=? AND date_jour_ref=?";
        String[] selectArgs = {
                String.valueOf(employee.getRegistrationNumber()),"DATE('NOW','LOCALTIME')"
        };
        Cursor cursor = Database.rawQuery(query, selectArgs);
                if (cursor.getCount() == 1)
            return false;
        return true;
    }

    public boolean hasNotClockedOut(Employee employee) {
        open();
        String query = "SELECT heure_sortie FROM pointage WHERE matricule=? AND date_jour_ref=?";
        String[] selectArgs = {
                String.valueOf(employee.getRegistrationNumber()), "DATE('NOW','LOCALTIME')"
        };
        Cursor cursor = Database.rawQuery(query, selectArgs);
        cursor.moveToFirst();

        if (cursor.getCount() == 0 || cursor.getString(3).equals( ""))
            return true;
        return false;
    }


}