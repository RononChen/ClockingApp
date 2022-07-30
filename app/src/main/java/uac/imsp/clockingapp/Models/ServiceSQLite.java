package uac.imsp.clockingapp.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ServiceSQLite  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Clocking_database.db";
    private static final int DATABASE_VERSION = 1;
        private static final String TABLE_SERVICE = "service";
    private static final String COL_ID_SERVICE = "id_service";
    private static final String COL_NOM_SERVICE="nom";


    private static final String CREATE_SERVICE = "CREATE TABLE " +
            TABLE_SERVICE + " (" +
            COL_ID_SERVICE + " INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT, " +
            COL_NOM_SERVICE + " TEXT NOT NULL  "+")" ;





    public ServiceSQLite(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
                db.execSQL(CREATE_SERVICE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+TABLE_SERVICE);
        onCreate(db);

    }
}
