package uac.imsp.clockingapp.Models.dbAdapter;

import static uac.imsp.clockingapp.Models.dbAdapter.ClockingSQLite.CREATE_CLOCKING;
import static uac.imsp.clockingapp.Models.dbAdapter.ClockingSQLite.DROP_CLOCKING;
import static uac.imsp.clockingapp.Models.dbAdapter.DaySQLite.CREATE_DAY;
import static uac.imsp.clockingapp.Models.dbAdapter.DaySQLite.DROP_DAY;
import static uac.imsp.clockingapp.Models.dbAdapter.PlanningSQLite.CREATE_PLANNING;
import static uac.imsp.clockingapp.Models.dbAdapter.PlanningSQLite.DROP_PLANNING;
import static uac.imsp.clockingapp.Models.dbAdapter.PlanningSQLite.planning;
import static uac.imsp.clockingapp.Models.dbAdapter.ServiceSQLite.CREATE_SERVICE;
import static uac.imsp.clockingapp.Models.dbAdapter.ServiceSQLite.DROP_SERVICE;
import static uac.imsp.clockingapp.Models.entity.Employee.HEAD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    public static final String COL_PHOTO = "photo";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_TYPE="type";
    public static final String COL_ADD_DATE="date_ajout";
    public static final String COL_ID_PLANNING_REF= "id_planning_ref";
        public static final String COL_ID_PLANNING="id_planning";
   private static final String COL_ID_SERVICE_REF = "id_service_ref";
    private static final String COL_ID_SERVICE = "id_service";
    private static final String COL_STATUS = "statut";
    private static final  String TABLE_TEMP="variable";

    public static final String super_user="INSERT INTO employe(matricule," +
            "username,password,type,sexe,couriel,id_service_ref," +
            "id_planning_ref,nom,prenom,birthdate,date_ajout)" +



            " VALUES (?,?,?,?,?,?,?,?,?,?,?,DATE('NOW','LOCALTIME'))";


public static final String CREATE_TEMP= "CREATE  TABLE IF NOT EXISTS variable" +
        " AS SELECT '1970-01-01' AS last_update ";
    public static final String CREATE_EMPLOYEE = "CREATE TABLE  IF NOT EXISTS " + TABLE_EMPLOYE + " (" +
            COL_MATRICULE + " INTEGER NOT NULL  PRIMARY KEY, " +
            COL_NOM + " TEXT NOT NULL ," +
            COL_PRENOM + " TEXT NOT NULL," +
            COL_SEXE + " TEXT NOT NULL ," +
            COL_EMAIL + " TEXT UNIQUE NOT NULL," +
           COL_BIRTHDATE+" TEXT , "+
            COL_PHOTO + "  BLOB ," +
            COL_USERNAME + " TEXT UNIQUE NOT NULL ," +
            COL_PASSWORD + " TEXT NOT NULL , " +
            COL_TYPE+" TEXT DEFAULT 'Simple', "+
            COL_ID_PLANNING_REF + " INTEGER  , " +
            COL_ID_SERVICE_REF + " INTEGER   ," +
            COL_STATUS+" TEXT DEFAULT 'Hors Service',"+
            COL_ADD_DATE+" TEXT ,"+
            " FOREIGN KEY(" + COL_ID_SERVICE_REF +
            " ) REFERENCES service(" + COL_ID_SERVICE+" )," +
            " FOREIGN KEY(" + COL_ID_PLANNING_REF +
            " ) REFERENCES planning(" + COL_ID_PLANNING+" )" +
            ")" ;
    public static final String DROP_EMPLOYEE="DROP TABLE IF EXISTS "+TABLE_EMPLOYE;
    public  static final String DROP_TEMP="DROP TABLE IF EXISTS "+TABLE_TEMP;



    public EmployeeSQLite(Context context) {


        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


     createDatabase(db);

    }
    public void upgradeDatabase(SQLiteDatabase db){
        db.execSQL(DROP_EMPLOYEE);
        db.execSQL(DROP_SERVICE);
        db.execSQL(DROP_PLANNING);
        db.execSQL(DROP_DAY);
        db.execSQL(DROP_CLOCKING);
        db.execSQL(DROP_TEMP);
        onCreate(db);

    }

    public void createDatabase(SQLiteDatabase db){
        db.execSQL(CREATE_SERVICE);
        db.execSQL(CREATE_PLANNING);
        db.execSQL(CREATE_DAY);
        db.execSQL(CREATE_EMPLOYEE);
        db.execSQL(CREATE_CLOCKING);
      db.execSQL(CREATE_TEMP);

        SQLiteStatement statement= db.compileStatement(super_user);
        statement.bindLong(1,1);
        statement.bindString(2,"User10");
        statement.bindString(3,md5("Aab10%"));
        statement.bindString(4,HEAD);
        statement.bindString(5,"M");
        statement.bindString(6,"super@gmail.com");
        statement.bindLong(7,1);
        statement.bindLong(8,1);
        statement.bindString(9,"AKOBA");
        statement.bindString(10,"Patrick");
        statement.bindString(11,"1970-01-01");
        statement.execute();
        String service = "INSERT INTO service(nom)  VALUES (?)";
        statement= db.compileStatement(service);
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



        statement= db.compileStatement(planning);
        byte[] workDays= new byte[]{'T','T','T','T','T','F','F'};


        //08-17
        statement.bindString(1,"08:00");
        statement.bindString(2,"17:00");
        statement.bindBlob(3,workDays);

        statement.executeInsert();

        //08-18

        statement.bindString(1,"08:00");
        statement.bindString(2,"18:00");
        statement.bindBlob(3,workDays);

        statement.executeInsert();

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        upgradeDatabase(db);


    }
    public String md5(String password) {
        MessageDigest digest ;
        byte[] messageDigest;

        StringBuilder hexString;
        try{
            digest=java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            messageDigest=digest.digest();
            hexString=new StringBuilder();
            for (byte element:messageDigest) {
                hexString.append(Integer.toHexString(0xFF & element));
                return hexString.toString();

            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}


