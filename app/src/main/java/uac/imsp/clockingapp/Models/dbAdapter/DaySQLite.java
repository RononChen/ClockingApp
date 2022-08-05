package uac.imsp.clockingapp.Models.dbAdapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DaySQLite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Clocking_database.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_JOUR = "jour";
    public static final String COL_DATE_JOUR= "date_jour";
    public static final String COL_ID_JOUR="id_jour";




    private static final String CREATE_JOUR = "CREATE TABLE IF NOT EXISTS " + TABLE_JOUR + " (" +
            COL_ID_JOUR + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT , " +
            COL_DATE_JOUR+" TEXT )" ;




    public DaySQLite(Context context) {


        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_JOUR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_JOUR);
        onCreate(db);

    }
}

