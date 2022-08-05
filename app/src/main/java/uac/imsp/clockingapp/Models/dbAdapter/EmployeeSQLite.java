package uac.imsp.clockingapp.Models.dbAdapter;

import static uac.imsp.clockingapp.Models.entity.Employee.HEAD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class EmployeeSQLite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Clocking_database.db";
        public static final int DATABASE_VERSION = 1;

    public static final String TABLE_EMPLOYE = "employe";

    public static final String COL_MATRICULE = "matricule";
    public static final String COL_NOM = "nom";
    public static final String COL_PRENOM = "prenom";
    public static final String COL_SEXE = "sexe";
    public static final String COL_BIRTHDATE = "birthdate";
    public static final String COL_EMAIL = "couriel";
    public static final String COL_QRCODE = "qr_code";
    public static final String COL_PHOTO = "photo";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_TYPE="type";
    public static final String COL_ID_PLANNING_REF= "id_planning_ref";
        public static final String COL_ID_PLANNING="id_planning";
   private static final String COL_ID_SERVICE_REF = "id_service_ref";
    private static final String COL_ID_SERVICE = "id_service";

    public static final String super_user="INSERT INTO employe(matricule," +
            "username,password,type,sexe,couriel,id_service_ref,id_planning_ref,nom,prenom)" +

            " VALUES (?,?,?,?,?,?,?,?,?,?)";

    private static final String CREATE_EMPLOYEE = "CREATE TABLE  IF NOT EXISTS " + TABLE_EMPLOYE + " (" +
            COL_MATRICULE + " INTEGER NOT NULL  PRIMARY KEY, " +
            COL_NOM + " TEXT NOT NULL ," +
            COL_PRENOM + " TEXT NOT NULL," +
            COL_SEXE + " CHAR(1) NOT NULL ," +// replace by text


            COL_EMAIL + " TEXT UNIQUE NOT NULL," +
           COL_BIRTHDATE+" TEXT , "+
           COL_QRCODE + "  BLOB  UNIQUE ," +
            COL_PHOTO + "  BLOB ," +
            COL_USERNAME + " TEXT UNIQUE NOT NULL ," +
            COL_PASSWORD + " TEXT NOT NULL , " +
            COL_TYPE+" TEXT DEFAULT 'Simple', "+
            COL_ID_PLANNING_REF + " TEXT  , " +
            COL_ID_SERVICE_REF + " INTEGER   ," +
            " FOREIGN KEY(" + COL_ID_SERVICE_REF +
            " ) REFERENCES service(" + COL_ID_SERVICE+" )," +
            " FOREIGN KEY(" + COL_ID_PLANNING_REF +
            " ) REFERENCES planning(" + COL_ID_PLANNING+" )" +
            ")" ;



    public EmployeeSQLite(Context context) {


        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_EMPLOYEE);
      SQLiteStatement statement= db.compileStatement(super_user);
       statement.bindLong(1,1);
       statement.bindString(2,"User10");
       statement.bindString(3,"password");
       statement.bindString(4,HEAD);
        statement.bindString(5,"M");
        statement.bindString(6,"super@gmail.com");
        statement.bindLong(7,1);
        statement.bindLong(8,1);
        statement.bindString(9,"AKOBA");
        statement.bindString(10,"Patrick");
        statement.execute();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EMPLOYE);
                onCreate(db);

    }
}


