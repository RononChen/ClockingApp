package uac.imsp.clockingapp.Models.dbAdapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class ServiceSQLite  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Clocking_database.db";
    private static final int DATABASE_VERSION = 1;
        private static final String TABLE_SERVICE = "service";
    private static final String COL_ID_SERVICE = "id_service";
    private static final String COL_NOM_SERVICE="nom";


    private static final String CREATE_SERVICE = "CREATE TABLE IF NOT EXISTS  " +
            TABLE_SERVICE + " (" +
            COL_ID_SERVICE + " INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT, " +
            COL_NOM_SERVICE + " TEXT UNIQUE NOT NULL  "+")" ;


    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //super.onDowngrade(db, oldVersion, newVersion);
        //onUpgrade(db);
        db.setVersion(oldVersion);
    }

    public ServiceSQLite(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_SERVICE);
        String service = "INSERT INTO service(nom)  VALUES (?)";
        SQLiteStatement statement= db.compileStatement(service);
        //Direction
        statement.bindString(1,"Direction");
        statement.executeInsert();

        //Service scolarité
        statement.bindString(1,"Service scolarité");
        statement.executeInsert();

        //Secrétariat administratif
        statement.bindString(1,"Secrétariat administratif");
        statement.executeInsert();
        //Comptabilité
        statement.bindString(1,"Comptabilité");
        statement.executeInsert();
        //Service de coopération
        statement.bindString(1,"Service de coopération");

        statement.executeInsert();

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.setVersion(newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SERVICE);
        onCreate(db);



    }
}
