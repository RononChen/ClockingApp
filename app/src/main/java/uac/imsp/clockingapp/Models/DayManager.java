package uac.imsp.clockingapp.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class DayManager {
    private SQLiteDatabase Database = null;
    private DaySQLite daySQLite;
    public DayManager(Context context) {
        daySQLite = new DaySQLite(context);
    }

    public SQLiteDatabase open() {

        if (Database == null)
            Database = daySQLite.getWritableDatabase();
        return Database;
    }

    public void close() {
        if (Database != null && Database.isOpen())
            Database.close();
    }

    public void create(Day day){
        String query="INSERT INTO jour(date_jour) VALUES (?)";
        SQLiteStatement statement= Database.compileStatement(query);
        statement.bindString(1,day.getDate());
    }







}
