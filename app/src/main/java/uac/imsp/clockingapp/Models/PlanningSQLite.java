package uac.imsp.clockingapp.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


public class PlanningSQLite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Clocking_database.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_PLANNING = "planning";
    public static final String COL_ID_PLANNING = "id_planning";
    public static final String COL_HEURE_DEBUT_OFFICIELLE="heure_debut_officielle";
    public static final String COL_HEURE_FIN_OFFICIELLE="heure_fin_officielle";
    private static final String planning="INSERT INTO planning(id_planning," +
            "heure_debut_officielle,heure_fin_officielle)" +

           " VALUES (?,?,?)";

    private static final String CREATE_PLANNING = "CREATE TABLE " + TABLE_PLANNING + " (" +

            COL_ID_PLANNING + " INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT, " +
            COL_HEURE_DEBUT_OFFICIELLE + " TEXT ," +
            COL_HEURE_FIN_OFFICIELLE + " TEXT )" ;



    public PlanningSQLite(Context context) {


        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_PLANNING);
        SQLiteStatement statement= db.compileStatement(planning);
        statement.bindLong(1,1);
        statement.bindString(2,"08:00");
        statement.bindString(3,"17:00");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLANNING);
        onCreate(db);

    }
}

