package uac.imsp.clockingapp.Models.dbAdapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import uac.imsp.clockingapp.BuildConfig;


public class PlanningSQLite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Clocking_database.db";
        public static final int DATABASE_VERSION = BuildConfig.VERSION_CODE;
    public static final String TABLE_PLANNING = "planning";
    public static final String COL_ID_PLANNING = "id_planning";
    public static final String COL_HEURE_DEBUT_OFFICIELLE="heure_debut_officielle";
    public static final String COL_HEURE_FIN_OFFICIELLE="heure_fin_officielle";
    private static final String planning="INSERT INTO planning(" +
            "heure_debut_officielle,heure_fin_officielle)" +

           " VALUES (?,?)";

    private static final String CREATE_PLANNING = "CREATE TABLE IF NOT EXISTS " + TABLE_PLANNING + " (" +


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

        //08-17
        statement.bindString(1,"08:00");
        statement.bindString(2,"17:00");
        statement.executeInsert();

        //08-18

        statement.bindString(1,"08:00");
        statement.bindString(2,"18:00");
        statement.executeInsert();


        //create structure
        //readAndExecuteSQLScript(db,contex, R.raw.db_create);

        //insert initial date
        //readAndExecuteSQLScript(db,contex, R.raw.initial_data);

    }
    /*public void readAndExecuteSQLScript(SQLiteDatabase db,
                                        Context context,Integer sqlScriptResId)
    {
        //Ressources res = ctx.getRessources();
        //PRAGMA user_version=4;
    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLANNING);
        onCreate(db);

    }
}

